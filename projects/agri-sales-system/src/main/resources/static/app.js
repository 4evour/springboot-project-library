const { createApp } = Vue;

const api = axios.create({ baseURL: "/api" });

createApp({
  data() {
    return {
      currentUser: null,
      authMode: "login",
      authForm: { username: "", password: "", realName: "", phone: "" },
      view: "shop",
      message: "",
      categories: [],
      products: [],
      cart: [],
      orders: [],
      notices: [],
      users: [],
      filters: { categoryId: "", keyword: "" },
      checkoutForm: { receiverName: "", receiverPhone: "", address: "" },
      categoryForm: { id: null, name: "", description: "" },
      productForm: { id: null, categoryId: "", name: "", description: "", price: "", stock: "", imageUrl: "", status: "ON_SALE" },
      noticeForm: { id: null, title: "", content: "" }
    };
  },
  computed: {
    isAdmin() {
      return this.currentUser && this.currentUser.role === "ADMIN";
    }
  },
  mounted() {
    this.loadMe();
    this.loadAll();
  },
  methods: {
    async request(promise) {
      const res = await promise;
      if (!res.data.success) throw new Error(res.data.message || "操作失败");
      return res.data.data;
    },
    show(text) {
      this.message = text;
      setTimeout(() => this.message = "", 2600);
    },
    async loadMe() {
      try {
        const data = await this.request(api.get("/auth/me"));
        this.currentUser = data;
      } catch (e) {
        this.currentUser = null;
      }
    },
    async submitAuth() {
      try {
        if (this.authMode === "login") {
          this.currentUser = await this.request(api.post("/auth/login", this.authForm));
        } else {
          await this.request(api.post("/auth/register", this.authForm));
          this.authMode = "login";
          this.show("注册成功，请登录");
        }
      } catch (e) {
        this.show(e.message);
      }
    },
    async logout() {
      await this.request(api.post("/auth/logout"));
      this.currentUser = null;
      this.view = "shop";
    },
    async loadAll() {
      await Promise.all([this.loadCategories(), this.loadProducts(), this.loadNotices()]);
    },
    async loadCategories() {
      this.categories = await this.request(api.get("/categories"));
    },
    async loadProducts() {
      this.products = await this.request(api.get("/products", { params: this.filters }));
    },
    async loadNotices() {
      this.notices = await this.request(api.get("/notices"));
    },
    async addCart(productId) {
      try {
        await this.request(api.post("/cart", { productId, quantity: 1 }));
        this.show("已加入购物车");
      } catch (e) {
        this.show(e.message);
      }
    },
    async loadCart() {
      this.cart = await this.request(api.get("/cart"));
    },
    async updateCart(item) {
      try {
        await this.request(api.put(`/cart/${item.id}`, { quantity: item.quantity }));
        this.show("数量已更新");
      } catch (e) {
        this.show(e.message);
        await this.loadCart();
      }
    },
    async deleteCart(id) {
      try {
        await this.request(api.delete(`/cart/${id}`));
        await this.loadCart();
      } catch (e) {
        this.show(e.message);
      }
    },
    async checkout() {
      try {
        await this.request(api.post("/orders/checkout", this.checkoutForm));
        this.checkoutForm = { receiverName: "", receiverPhone: "", address: "" };
        await this.loadCart();
        this.show("订单提交成功");
      } catch (e) {
        this.show(e.message);
      }
    },
    async loadMyOrders() {
      this.orders = await this.request(api.get("/orders/mine"));
    },
    async loadAdminOrders() {
      this.orders = await this.request(api.get("/admin/orders"));
    },
    async updateOrderStatus(order) {
      try {
        await this.request(api.put(`/admin/orders/${order.id}/status`, { status: order.status }));
        this.show("订单状态已更新");
      } catch (e) {
        this.show(e.message);
        await this.loadAdminOrders();
      }
    },
    editCategory(category) {
      this.categoryForm = { ...category };
    },
    async saveCategory() {
      try {
        await this.request(api.post("/admin/categories", this.categoryForm));
        this.categoryForm = { id: null, name: "", description: "" };
        await this.loadCategories();
        this.show("分类已保存");
      } catch (e) {
        this.show(e.message);
      }
    },
    editProduct(product) {
      this.productForm = { ...product };
    },
    async saveProduct() {
      try {
        await this.request(api.post("/admin/products", this.productForm));
        this.productForm = { id: null, categoryId: "", name: "", description: "", price: "", stock: "", imageUrl: "", status: "ON_SALE" };
        await this.loadProducts();
        this.show("商品已保存");
      } catch (e) {
        this.show(e.message);
      }
    },
    async deleteProduct(id) {
      try {
        await this.request(api.delete(`/admin/products/${id}`));
        await this.loadProducts();
        this.show("商品已删除");
      } catch (e) {
        this.show(e.message);
      }
    },
    async loadUsers() {
      this.users = await this.request(api.get("/admin/users"));
    },
    async toggleUser(user) {
      const status = user.status === "ACTIVE" ? "DISABLED" : "ACTIVE";
      try {
        await this.request(api.put(`/admin/users/${user.id}/status`, { status }));
        await this.loadUsers();
        this.show("用户状态已更新");
      } catch (e) {
        this.show(e.message);
      }
    },
    editNotice(notice) {
      this.noticeForm = { ...notice };
    },
    async saveNotice() {
      try {
        await this.request(api.post("/admin/notices", this.noticeForm));
        this.noticeForm = { id: null, title: "", content: "" };
        await this.loadNotices();
        this.show("公告已保存");
      } catch (e) {
        this.show(e.message);
      }
    }
  }
}).mount("#app");
