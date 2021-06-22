<template>
  <div class="container">
    <header class="jumbotron">
      <h3>
        Profile
      </h3>
    </header>
    <p>
      <strong>Username:</strong>
      {{currentUser.username}}
    </p>

    <p>
      <strong>Authority:</strong>
      {{currentUser.authority}}
    </p>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push('/login');
    }
  }
};
</script>