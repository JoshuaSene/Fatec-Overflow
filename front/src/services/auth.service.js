import axios from 'axios';

const API_URL = 'http://localhost:8080/';

 
class AuthService {
  login(user) {
    return axios
      .post(API_URL + 'login', {
        username: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.token) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }
        
        return response.data;
      }).catch(err => {
        console.log(err);
        alert(err)
      });
      
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(user) {
    return axios.post(API_URL + 'user', {
      name: user.name,
      register: user.register,
      username: user.username, 
      password: user.password,
      role: user.role
    });
  }

  createPost(post, userId) {
    return axios.post(API_URL + 'post', {
      title: post.title,
      author: userId,
      content: post.content
    });
  }
}

export default new AuthService();
