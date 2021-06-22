<template>
  <div class="container">
    <header class="jumbotron">
      <h3>{{ title }}</h3>
    </header>

    <div id="buttons">
      <div v-if="isAdmin">
        <button @click="$router.push('/user-list')" class="btn btn-dark btns">Usuários</button>
        <!-- <router-link to="/user-list" tag="button"></router-link> -->
      </div>
      <button @click="$router.push('/post-list')" class="btn btn-dark btns">Posts</button>
      <!-- <router-link to="/post-list" tag="button">Posts</router-link> -->
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      title: "HOME",
      users: [],
      posts: []
    };
  },
  computed: {
    isAdmin() {     
      if(this.$store.state.auth.user != null) {
        return this.$store.state.auth.user.authority === 'ROLE_TEACHER' || this.$store.state.auth.user.authority === 'ROLE_ADM';
      } else {
        return false;
      }
    },
    isStudent() {
      if(this.$store.state.auth.user != null) {
        return this.$store.state.auth.user.authority === 'ROLE_STUDENT';
      } else {
        return false;
      }
    }
  }
};
</script>

<style scoped>
.btns {
  float: left;
  margin-right: 1%;
}
</style>