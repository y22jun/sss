<script setup>
import { ref } from "vue"
import WeekView from "@/components/WeekComponent.vue";

const isVisible = ref(false);

const toggleVisible = (value) => {
  isVisible.value = value;
}
</script>
<template>
  <header id="header">
    <h1>그룹내 게시판 테스트 페이지</h1>
    <br>
  </header>
  
  <!-- 네비게이션 바 -->
  <!-- <ul class="nav nav-tabs navigation-bar">
    <li class="nav-item">
      <a class="nav-link active" aria-current="page" href="#">Active</a>
    </li>
    <li class="nav-item dropdown">
      <a
        class="nav-link dropdown-toggle"
        data-bs-toggle="dropdown"
        href="#"
        role="button"
        aria-expanded="false"
        >Dropdown</a
      >
      <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="#">Action</a></li>
        <li><a class="dropdown-item" href="#">Another action</a></li>
        <li><a class="dropdown-item" href="#">Something else here</a></li>
        <li><hr class="dropdown-divider" /></li>
        <li><a class="dropdown-item" href="#">Separated link</a></li>
      </ul>
    </li>
    <li class="nav-item right">
      <button
        class="nav-link"
        data-bs-toggle="modal"
        data-bs-target="#add-problem"
      >
        문제추가
      </button>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Link</a>
    </li>
    <li class="nav-item">
      <a class="nav-link disabled">Disabled</a>
    </li>
  </ul> -->
  <!-- 네이게이션 끝 -->

  <!-- 목차 설명 -->
  <div class="row">
    <div class="col">1</div>
    <div class="col-2">2</div>
  </div>
  
  <div class="row">
    <!-- 오픈 스터디 게시판 -->
    <div class="col">
      <div class="card p-2 m-1 group-list" >
        <h1>오픈 스터디 게시판</h1>
      </div>
    </div>
  <!-- 오픈 스터디 게시판 -->
    <div class="col-2">
      <div class="card p-2 m-1 group-side-bar">
        
      </div>
    </div>
  </div>
</template>

<style scoped>
.right {
  position: absolute;
  right: 0px;
}
.navigation-bar {
  position: relative;
  display: flex;
  overflow: visible;
  flex-direction: row;
  justify-content: left;
}


#header {
  width: 100%;
  height: 40%;
}

#header h1 {
  font-size: 50px;
  font-weight: bold;
  padding-bottom: 20px;
}
.group-list{
  height: 80vh;
}
.group-side-bar{
  height: 80vh;
}
</style>
