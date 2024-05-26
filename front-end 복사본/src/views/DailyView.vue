<script setup>
import { ref, onMounted} from "vue";
import { useRouter, useRoute } from "vue-router";
import { api } from "@/utils/axios";

const router = useRouter();
const route = useRoute();
const problemSource = ref("BAEKJOON");
const ProblemNumber = ref();
const problemInfo = ref();
const searching = ref(false);
const sortItem = ref();
const isMount = ref(false);

function changeProblemSource(value) {
  problemSource.value = value;
}
async function searchProblem() {
  searching.value = true;
  // 번호가 숫자인지 확인

  // 문제 조회
  const result = await api.solved.searchOne(ProblemNumber.value);
  console.log(result[0]);

  // setTimeout(() => {
  //   searching.value = false;
  // }, 3000); // 3초 대기
  
  searching.value = false;
  //3초안에 못찾은 경우
}
function moveProblemView(value) {
  router.push({ name: "problem-view", params: { id: value } });
}
const selectedSortItem = ref("정렬");
const updateSortItem = (name) => {
    selectedSortItem.value = name;
}


onMounted(() => {
    
    setTimeout(() => {
      isMount.value = true; // n초 후에 isMount 값을 false로 변경
    }, 100);
  });
</script>

<template>
  <!-- id 테스트 -->
  <header id="header">
    <h1 :class="{mount : isMount}">데일리 문제 리스트</h1>
  </header>

  <main class="row">
    <div class="problist col-8">
      <div class="card-form col">
        <!-- 목차 -->
        <div class="div nav navigation-bar col">
          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              data-bs-toggle="dropdown"
              href="#"
              role="button"
              aria-expanded="false"
              >{{selectedSortItem}}</a
            >
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" @click="updateSortItem('액션')" href="#">액션</a></li>
              <li><a class="dropdown-item" @click="updateSortItem('가면')" href="#">가면</a></li>
              <li><a class="dropdown-item" @click="updateSortItem('출동')" href="#">출동</a></li>
              <!-- <li><hr class="dropdown-divider" /></li> -->
            </ul>
          </li>
          <!-- 안푼 문제만 -->
          
          <li class="nav-item">
            <a class="nav-link" href="#" style="cursor: pointer;">해결</a>
          </li>
          
        </div>  
        <div class="problem-items">
          <div class="problem-item" @click="moveProblemView(1)">
            <div class="img-container">
              <img src="../assets/logo.svg" alt="error-image" class="rank-img" />
            </div>
            <div class="vr"></div>
            <p class="problem-source">출처</p>
            <div class="vr"></div>
            <p class="problem-name">문제 이름</p>
            <div class="vr"></div>
            <!--알고리즘 차후 추가
            <p class="problem-solve-algorithm"> 알고리즘 </p>
            <div class="vr"></div>
            -->
            <p class="problem-solve">품</p>
          </div>

          <div class="problem-item" @click="moveProblemView(221)">
            <div class="img-container">
              <img src="../assets/logo.svg" alt="error-image" class="rank-img" />
            </div>
              <div class="vr"></div>
              <p class="problem-source">출처</p>
              <div class="vr"></div>
              <p class="problem-name">문제 이름</p>
              <div class="vr"></div>
              <p class="problem-solve">품</p>
            </div>
          </div>
        </div>
      </div>
      
      <div class="card-form col p-4">
        Rank
        <div class="personal-container pt-5">
          <div
            class="progress"
            role="progressbar"
            aria-label="Example with label"
            aria-valuenow="25"
            aria-valuemin="0"
            aria-valuemax="100"
          >
            <div class="progress-bar" style="width: 25%">25%</div>
          </div>
          <br />
          <div
            class="progress"
            role="progressbar"
            aria-label="Example with label"
            aria-valuenow="25"
            aria-valuemin="0"
            aria-valuemax="100"
          >
            <div class="progress-bar" style="width: 25%">25%</div>
          </div>
          <br />
          <div
            class="progress"
            role="progressbar"
            aria-label="Example with label"
            aria-valuenow="25"
            aria-valuemin="0"
            aria-valuemax="100"
          >
            <div class="progress-bar" style="width: 25%">25%</div>
          </div>
        </div>
      </div>
    </main>
    <!-- <ul class="nav nav-tabs navigation-bar ">
      <li class="nav-item">
        <a class="nav-link active" aria-current="page" href="#">문제</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
      정렬
      <li class="nav-item dropdown">
        <a
          class="nav-link dropdown-toggle"
          data-bs-toggle="dropdown"
          href="#"
          role="button"
          aria-expanded="false"
          >정렬</a
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
        역할 확인
        <button
          class="nav-link"
          data-bs-toggle="modal"
          data-bs-target="#add-problem"
        >
          문제추가
        </button>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled">Disabled</a>
      </li>
    </ul> -->

    <!-- 아이템 -->
    
    <hr />
    <!-- <div>
      <button type="button" class="btn btn-outline-primary">Left</button>
      <button type="button" class="btn btn-outline-primary">Middle</button>
      <button type="button" class="btn btn-outline-primary">Right</button>
    </div> -->

    <!-- Modal -->
    <div
      class="modal fade"
      id="add-problem"
      tabindex="-1"
      aria-labelledby="exampleModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">문제 추가</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body add-problem-container">
            <div class="input-group mb-2">
              <button
                class="input-group-text dropdown-toggle"
                id="basic-addon1"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
              {{ problemSource }}
            </button>
              <ul class="dropdown-menu">
                <!-- Dropdown menu links -->
                <li>
                  <a
                    class="dropdown-item"
                    @click="changeProblemSource('BAEKJOON')"
                    >BAEKJOON</a
                  >
                </li>
                <li>
                  <a class="dropdown-item" @click="changeProblemSource('SWEA')"
                    >SWEA</a
                  >
                </li>
              </ul>

              <input
                type="text"
                v-model="ProblemNumber"
                class="form-control"
                placeholder="문제 번호"
                aria-label="Problem No"
                aria-describedby="basic-addon1"
              />

              <!-- 문제 검색 -->
              <button 
                type="button"
                class="input-group-text"
                id="basic-addon2"
                @click="searchProblem()"
              >
                검색
              </button >
            </div>

            <!-- 불러온 정보 입력 -->
            <div
              v-if="searching"
              class="spinner-border text-primary"
              role="status"
            >
              <span class="visually-hidden">Loading...</span>
            </div>
          </div>

          <div class="modal-footer">
            <!-- 추가시 -->
            <button type="button" class="btn btn-primary">Save changes</button>
            <!-- 종료 시-->
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
          </div>
        </div>
      </div>
    </div>
  <!-- 개인별 성취도 -->

</template>

<style scoped>
/* 헤더 */

#header {
  width: 100%;
  height: 40%;
}

h1 {
  font-size: 30px;
  padding-bottom: 20px;
}
.mount{
  font-size: 40px;
  font-weight: bold;
  /* color: rgb(197, 197, 255); */
  transition: font-size 1s ease, color 0.5s ease, font-weight 0.5s ease;
}

/* 카드 폼  */
.card-form{
  border: 1px solid lightgray;
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.1);
  min-height: 70vh;
  margin: 4px;
  padding: 4px;
}
.btn-size {
  width: 100px;
}

p {
  margin: 0px 0px 0px 0px;
  line-height: 120%;
}
.problem-item {
  display: flex;
  flex-direction: row;
  position: relative;
  margin-top: 0.3%;
  padding: 10px;
  border-radius: 5px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);

  text-align: center;
  width: 100%;
  height: 40px;
}
.problem-item:hover {
  background-color: rgba(0, 0, 0, 0.1);
}

.problem-source {
  flex: 0.05;
}
.img-container {
  flex: 0.05;
  position: relative;
}
.rank-img {
  vertical-align: 0;
  width: 100%;
  height: 100%;
}
.problem-name {
  flex: 0.85;
  text-align: left;
  margin-left: 1%;
}
.problem-solve-algorithm {
  text-align: center;
  flex: 0.15;
}
.problem-solve {
  flex: 0.05;
}

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

.btn-group-vertical {
  max-height: 100vh;
  width: 100%;
}
.btn {
  width: 100%;
  text-align: left;
}
</style>