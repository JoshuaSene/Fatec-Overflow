import AuthService from '../services/auth.service';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
  ? { status: { loggedIn: true }, user: user }
  : { status: { loggedIn: false }, user: null };

export const auth = {
  namespaced: true,
  state: initialState,
  actions: {
    login({ commit }, user) {
      return AuthService.login(user).then(
        user => {
          if(user !== undefined && user !== null) {
            commit('loginSuccess', user);
            return Promise.resolve(user);
          }
          commit('loginFailure');
          return Promise.reject('Falha no login');
        },
        error => {
          commit('loginFailure');
          return Promise.reject(error);
        }
      );
    },
    logout({ commit }) {
      AuthService.logout();
      commit('logout');
    },
    register({ commit }, user) {
      return AuthService.register(user).then(
        response => {
          if(response !== undefined
            && response !== null
            && response.data !== null
            && response.data !== undefined) {

            commit('registerSuccess');
            return Promise.resolve(response.data);
          }
          commit('registerFailure');
          return Promise.reject('Falha ao criar usuário');
        },
        error => {
          commit('registerFailure');
          return Promise.reject(error);
        }
      );
    },
    createPost(post, id) {
      return AuthService.createPost(post, id).then(
        response => {
          if(response !== undefined
            && response !== null
            && response.data !== null
            && response.data !== undefined) {
            return Promise.resolve(response.data);
          }
          return Promise.reject('Erro ao criar Post');
        },
        error => {
          return Promise.reject(error);
        }
      );
    }
  },
  mutations: {
    loginSuccess(state, user) {
      state.status.loggedIn = true;
      state.user = user;
    },
    loginFailure(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    logout(state) {
      state.status.loggedIn = false;
      state.user = null;
    },
    registerSuccess(state) {
      state.status.loggedIn = false;
    },
    registerFailure(state) {
      state.status.loggedIn = false;
    }
  }
};
