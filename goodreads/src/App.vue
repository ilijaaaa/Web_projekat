<template>
  <nav>
    <router-link to="/">Početna</router-link> |
    <router-link to="/pretragaKnjige">Knjige</router-link> |
    <router-link to="/zanrovi">Žanrovi</router-link> |
    <router-link to="/profil">Profil</router-link> |
    <router-link to="/about">O nama</router-link>
  </nav>
  <router-view />
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #007bff;
}
</style>