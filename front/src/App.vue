<template>
<div id="app">
  <nav class="navbar navbar-expand navbar-dark bg-dark">
    <a href="/" class="navbar-brand">FatecOverFlow</a>
    <div class="navbar-nav mr-auto">
      <li class="nav-item">
        <router-link to="/home" class="nav-link">
          <font-awesome-icon icon="home" /> Home
        </router-link>
      </li>

      <li v-if="showAdmBoard" class="nav-item">
        <router-link to="/user-list" class="nav-link">User List</router-link>
      </li>

      <li class="nav-item">
        <router-link to="/post-list" class="nav-link">Posts</router-link>
      </li>
    </div>

    <div v-if="!currentUser" class="navbar-nav ml-auto">
      <li class="nav-item">
        <router-link to="/register" class="nav-link">
          <font-awesome-icon icon="user-plus" /> Sign Up
        </router-link>
      </li>
      <li class="nav-item">
        <router-link to="/login" class="nav-link">
          <font-awesome-icon icon="sign-in-alt" /> Login
        </router-link>
      </li>
    </div>

    <div v-if="currentUser" class="navbar-nav ml-auto">
      <li class="nav-item">
        <router-link to="/profile" class="nav-link">
          <font-awesome-icon icon="user" />
          {{ currentUser.username }}
        </router-link>
      </li>
      <li class="nav-item">
        <a class="nav-link" @click.prevent="logOut">
          <font-awesome-icon icon="sign-out-alt" /> LogOut
        </a>
      </li>
    </div>
  </nav>

  <div class="container">
    <router-view />
  </div>
</div>
</template>

<script>
export default {
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showStudentBoard() {
      if (this.currentUser && this.currentUser['authority']) {
        return this.currentUser['authority'].includes('ROLE_STUDENT');
      }

      return false;
    },
    showTeacherBoard() {
      if (this.currentUser && this.currentUser['authority']) {
        return this.currentUser['authority'].includes('ROLE_TEACHER');
      }

      return false;
    },
    showAdmBoard() {
      if (this.currentUser && this.currentUser['authority']) {
        return this.currentUser['authority'].includes('ROLE_ADM') ||
          this.currentUser['authority'].includes('ROLE_TEACHER');
      }

      return false;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>
