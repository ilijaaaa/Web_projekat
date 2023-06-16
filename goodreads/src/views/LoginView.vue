<template>
  <div class="container">
    <h1 class="heading">Goodreads</h1>
    <h2 class="subheading">Prijava</h2>
    <form @submit.prevent="login" class="form">
      <div class="form-group">
        <input type="email" v-model="email" placeholder="Mejl" required />
      </div>
      <div class="form-group">
        <input type="password" v-model="password" placeholder="Lozinka" required />
      </div>
      <button type="submit" class="submit-button">Prijava</button>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </form>
  </div>
</template>
  
<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: '',
      password: '',
      errorMessage: '',
    };
  },
  methods: {
    login() {
      const signInDto = {
        mejl: this.email,
        lozinka: this.password
      };
      axios.post('http://localhost:8080/api/login', signInDto)
        .then(response => {
          console.log(response.data);
          localStorage.setItem("korisnik", response.data.korisnik.sessionId);
          this.$router.push("/korisnik?id=" + response.data.korisnik.id);
        })
        .catch(error => {
          console.error(error);
          if (error.response && error.response.data)
            this.errorMessage = error.response.data;
        });
    }
  }
};
</script>
  
<style scoped>
.container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
  background-color: #f7f7f7;
}

.heading {
  font-size: 38px;
  margin-bottom: 10px;
}

.subheading {
  font-size: 20px;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

input[type="text"],
input[type="email"],
input[type="password"] {
  width: 85%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submit-button {
  width: 30%;
  padding: 10px;
  font-size: 16px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #0056b3;
}

.error {
  color: red;
  margin-top: 10px;
}
</style>