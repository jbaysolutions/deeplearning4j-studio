<template>
  <div class="sidebar-sticky">
    <ul class="nav flex-column">
      <li class="nav-item">
        <router-link class="nav-link active"  :to="{ name: 'Dashboard'}">
          <i class="fas fa-home"></i>
          Dashboard
        </router-link>
      </li>
      <li class="nav-item">
        <router-link class="nav-link active" :to="{ name: 'Models'}">
          <i class="fa fa-project-diagram"></i>
          Models
        </router-link>
      </li>
      <li class="nav-item">
        <a class="nav-link active" href="#">
          <i class="fas fa-file-alt"></i>
          Logs
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" href="#">
          <i class="fas fa-cogs"></i>
          Settings
        </a>
      </li>
    </ul>
  </div>
</template>
