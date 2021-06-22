import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/';

class UserService {
  getPosts() {
    return axios.get(API_URL + 'post', { headers: authHeader() });
  }

  getUsers() {
    return axios.get(API_URL + 'user', { headers: authHeader() });
  }

  getStudentBoard() {
    return axios.get(API_URL + 'student', { headers: authHeader() });
  }

  getTeacherBoard() {
    return axios.get(API_URL + 'teacher', { headers: authHeader() });
  }

  getAdmBoard() {
    return axios.get(API_URL + 'adm', { headers: authHeader() });
  }
}

export default new UserService();
