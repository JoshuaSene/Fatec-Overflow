<template>
  <div class="container">
    <header class="jumbotron">
      <h3>{{ title }}</h3>
    </header>

    <div id="content">
      <ul id="users">
        <li v-for="user in users" :key="user.id">
          {{ user.name }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: "User List",
  data() {
    return {
      title: "User List",
      users: []
    };
  },
  mounted() {
    UserService.getUsers().then(
      (response) => {
        this.users = response.data;
      },
      (error) => {
        let errMessage =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
        
        alert(errMessage);
      }
    );
  },
};
</script>
