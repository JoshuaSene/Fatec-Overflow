import vSelect from 'vue-select'
import { createApp } from "vue";

import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import App from "./App.vue";
import store from "./store";
import router from "./router";
import UserList from "./components/UserList.vue"
import PostList from "./components/PostList.vue"
import { FontAwesomeIcon } from './plugins/font-awesome'

createApp(App)
  .use(router)
  .use(store)
  .component("font-awesome-icon", FontAwesomeIcon)
  .component('v-select', vSelect)
  .component('user-list', UserList)
  .component('post-list', PostList)
  .mount("#app");

  export default function authHeader() {
    let user = JSON.parse(localStorage.getItem('user'));
    
    if (user && user.token) {
      return { Authorization: 'Bearer ' + user.token };
    } else {
      return {};
    }
  }