const { createApp } = Vue;

const api = axios.create({ baseURL: "/api" });

createApp({
  data() {
    return {
      currentUser: null,
      authMode: "login",
      authForm: { username: "", password: "", realName: "", phone: "" },
      view: "animals",
      message: "",
      animals: [],
      rescues: [],
      adoptions: [],
      notices: [],
      feedback: [],
      users: [],
      animalForm: { id: null, name: "", species: "", gender: "", age: "", location: "", healthStatus: "", description: "", imageUrl: "", status: "WAITING" },
      rescueForm: { animalId: "", title: "", location: "", description: "" },
      adoptionForm: { animalId: "", reason: "" },
      feedbackForm: { content: "" },
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
    animalStatusText(status) {
      return ({ WAITING: "待领养", RESCUING: "救助中", ADOPTED: "已领养" })[status] || status || "未设置";
    },
    rescueStatusText(status) {
      return ({ SUBMITTED: "已提交", PROCESSING: "处理中", DONE: "已完成" })[status] || status || "未设置";
    },
    adoptionStatusText(status) {
      return ({ PENDING: "待审核", APPROVED: "已通过", REJECTED: "已拒绝" })[status] || status || "未设置";
    },
    roleText(role) {
      return ({ ADMIN: "管理员", USER: "普通用户" })[role] || role || "未设置";
    },
    userStatusText(status) {
      return ({ ACTIVE: "正常", DISABLED: "已禁用" })[status] || status || "未设置";
    },
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
        this.currentUser = await this.request(api.get("/auth/me"));
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
      this.view = "animals";
    },
    async loadAll() {
      await Promise.all([this.loadAnimals(), this.loadNotices()]);
    },
    async loadAnimals() {
      this.animals = await this.request(api.get("/animals"));
    },
    async loadNotices() {
      this.notices = await this.request(api.get("/notices"));
    },
    fillAdoption(animal) {
      this.adoptionForm.animalId = animal.id;
      this.view = "adoptions";
    },
    async submitRescue() {
      try {
        await this.request(api.post("/rescues", this.rescueForm));
        this.rescueForm = { animalId: "", title: "", location: "", description: "" };
        await this.loadMyRescues();
        this.show("救助信息已提交");
      } catch (e) {
        this.show(e.message);
      }
    },
    async loadMyRescues() {
      this.rescues = await this.request(api.get("/rescues/mine"));
    },
    async loadAdminRescues() {
      this.rescues = await this.request(api.get("/admin/rescues"));
    },
    async updateRescue(item) {
      try {
        await this.request(api.put(`/admin/rescues/${item.id}/status`, { status: item.status }));
        this.show("救助状态已更新");
      } catch (e) {
        this.show(e.message);
        await this.loadAdminRescues();
      }
    },
    async submitAdoption() {
      try {
        await this.request(api.post("/adoptions", this.adoptionForm));
        this.adoptionForm = { animalId: "", reason: "" };
        await this.loadMyAdoptions();
        this.show("领养申请已提交");
      } catch (e) {
        this.show(e.message);
      }
    },
    async loadMyAdoptions() {
      this.adoptions = await this.request(api.get("/adoptions/mine"));
    },
    async loadAdminAdoptions() {
      this.adoptions = await this.request(api.get("/admin/adoptions"));
    },
    async updateAdoption(item) {
      try {
        await this.request(api.put(`/admin/adoptions/${item.id}/status`, { status: item.status }));
        this.show("领养状态已更新");
      } catch (e) {
        this.show(e.message);
        await this.loadAdminAdoptions();
      }
    },
    async submitFeedback() {
      try {
        await this.request(api.post("/feedback", this.feedbackForm));
        this.feedbackForm = { content: "" };
        await this.loadMyFeedback();
        this.show("反馈已提交");
      } catch (e) {
        this.show(e.message);
      }
    },
    async loadMyFeedback() {
      this.feedback = await this.request(api.get("/feedback/mine"));
    },
    async loadAdminFeedback() {
      this.feedback = await this.request(api.get("/admin/feedback"));
    },
    async replyFeedback(item) {
      try {
        await this.request(api.put(`/admin/feedback/${item.id}/reply`, { reply: item.reply }));
        this.show("回复已保存");
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
    },
    async saveAnimal() {
      try {
        await this.request(api.post("/admin/animals", this.animalForm));
        this.animalForm = { id: null, name: "", species: "", gender: "", age: "", location: "", healthStatus: "", description: "", imageUrl: "", status: "WAITING" };
        await this.loadAnimals();
        this.show("动物信息已保存");
      } catch (e) {
        this.show(e.message);
      }
    },
    editAnimal(animal) {
      this.animalForm = { ...animal };
    },
    async deleteAnimal(id) {
      try {
        await this.request(api.delete(`/admin/animals/${id}`));
        await this.loadAnimals();
        this.show("动物信息已删除");
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
    }
  }
}).mount("#app");
