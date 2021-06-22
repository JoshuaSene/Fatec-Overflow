<template>
  <div class="container">
    <header class="jumbotron">
      <h3>{{ title }}</h3>
    </header>
         
    <div id="content">
      <ul id="posts">
        <li v-for="post in posts" :key="post.id">
          <h1>{{ post.title }}</h1>
          {{ post.content }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: "Post List",
  data() {
    return {
      title: "Post List",
      posts: []
    };
  },
  mounted() {
    UserService.getPosts().then(
      (response) => {
        this.posts = response.data;
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
