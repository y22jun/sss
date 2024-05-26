<script setup>
import { useRouter } from "vue-router";
import { api } from "@/utils/axios";
import { useFlagStore } from "@/stores/flag";
import { useSessionStore } from "@/stores/session";
import { onClickOutside } from "@vueuse/core";
import { onMounted, ref, watch } from "vue";

// pinia
const flagStore = useFlagStore();
const sessionStore = useSessionStore();

onMounted(async () => {
  await sessionStore.session();
});
const alert = ref(false);
const inputEmail = ref("");
const inputPassword = ref("");
const router = useRouter();
const modal = ref();

const inputemailIsValid = ref(true);
const inputPasswordIsValid = ref(true);
onClickOutside(modal, (event) => (flagStore.modalFlag = false));

async function goHomePage() {
  if(!inputPasswordIsValid.value){
    console.log(inputPasswordIsValid.value)
    alert.value = "비밀번호는 영문 소문자, 대문자, 특수 문자(!@#$%^) 중 최소 2개를 포함하여 8자 이상, 16자 이하여야 합니다.";
  }
  else{
    // 요청
    try {
      const result = await api.user.login(inputEmail.value, inputPassword.value);
      if (result != null) {
        // 로그인 해도돼?
        const loginflag = await sessionStore.login(result.email, result.token);
        if (loginflag) {
          flagStore.toggleModal();
          router.push({ name: "home" });
        } else {
          // 아이디 비밀번호 확인
          alert.value = true;
        }
      } else {
        alert.value = true;
      }
    } catch (error) {
      alert.value = "이메일 또는 비밀번호가 올바르지 않습니다. 입력한 내용을 다시 확인해 주세요.";
    }
  }
}

// 이메일 유효성
const validateInputEmail = () => {
  // 이메일 유효성 검사 로직을 여기에 추가
  const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
  inputemailIsValid.value = emailPattern.test(inputEmail.value);
};

// 비밀번호 유효성
const validateInputPassword = () => {
  const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()\-_=+{};:,<.>/?]).{8,16}$/;
  inputPasswordIsValid.value = passwordRegex.test(password.value);
};

watch(inputEmail, () => {
  validateInputEmail();
});

watch(inputPassword, () => {
  validateInputPassword();
});
</script>

<template>
  <!-- Login 7 - Bootstrap Brain Component -->
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-12 col-md-9 col-lg-7 col-xl-6 col-xxl-5">
        <div class="card border border-light-subtle rounded-4">
          <div class="card-body p-3 p-md-4 p-xl-5" ref="modal">
            <div class="row">
              <div class="col-12">
                <div class="mb-2">
                  <div class="text-center mb-4">
                    <img
                      id="logo"
                      src="../../public/logo-white.jpg"
                      alt="sofit-logo"
                      style="justify-self: center"
                    />
                  </div>
                </div>
              </div>
            </div>
            <!-- <form action="#!"> -->
            <div class="row gy-3 overflow-hidden">
              <div class="col-12">
                <div class="form-floating mb-3">
                  <input
                    v-model="inputEmail"
                    type="text"
                    class="form-control"
                    name="email"
                    id="email"
                    placeholder="name@example.com"
                    aria-describedbys="emailHelp"
                    :class="{'is-invalid' : !inputemailIsValid}"
                    required
                  />
                  <label for="email" class="form-label">Email</label>
                </div>
              </div>
              <div class="col-12">
                <div class="form-floating mb-3">
                  <input
                    v-model="inputPassword"
                    type="password"
                    class="form-control"
                    name="password"
                    id="password"
                    value=""
                    placeholder="Password"
                    :disabled="!inputemailIsValid || (inputEmail === '')"
                    :class="{'is-invalid' : !inputPasswordIsValid}"
                    required
                  />
                  <label for="password" class="form-label">Password</label>
                </div>
              </div>
              <p color="#FF4242" class="text-danger" v-if="alert">
                <font-awesome-icon
                  :icon="['fas', 'triangle-exclamation']"
                  style="color: #ff4242"
                />
                {{alert}}
              </p>
              <!-- <div class="col-12">
                      <div class="form-check">
                        <input
                          class="form-check-input"
                          type="checkbox"
                          value=""
                          name="remember_me"
                          id="remember_me"
                        />
                        <label
                          class="form-check-label text-secondary"
                          for="remember_me"
                        >
                          Keep me logged in
                        </label>
                      </div>
                    </div> -->
              <div class="col-12">
                <div class="d-grid">
                  <!-- 로그인 버튼 -->
                  <button
                    class="btn login-btn btn-primary"
                    type="submit"
                    @click="goHomePage()"
                    @keyup.enter="goHomePage()"
                  >
                    로그인
                  </button>
                </div>
              </div>
            </div>
            <!-- </form> -->
            <div class="row">
              <div class="col-12 mt-4">
                <div class="css-z0tt1n"><p data-testid="Typography" color="#000000" class="or">또는</p></div>
                <div
                  class="d-flex gap-2 gap-md-4 flex-column flex-md-row justify-content-md-end"
                >
                  <!-- 회원 가입 -->
                  <router-link
                    to="/sign"
                    class="link-secondary text-decoration-none"
                    @click="flagStore.toggleModal()"
                  >
                    회원 가입
                  </router-link>
                  <!-- 비밀번호 찾기 -->
                  <router-link
                    to="/password"
                    class="link-secondary text-decoration-none"
                    @click="flagStore.toggleModal()"
                  >
                    비밀번호 찾기
                  </router-link>
                </div>
              </div>
            </div>
            <!-- 소셜 로그인 -->
            <div class="row">
              <div class="col-12">
                <br />
                <div class="d-flex gap-2 gap-sm-3 justify-content-center">
                  <a href="#!" class="btn btn-lg btn-outline-danger p-3 lh-1">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="28"
                      height="28"
                      fill="currentColor"
                      class="bi bi-google"
                      viewBox="0 0 16 16"
                    >
                      <path
                        d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"
                      />
                    </svg>
                  </a>


                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 로고 */
#logo {
  width: 150px;
  height: 40px;
}

.invalid-feedback,
.valid-feedback {
  margin-left: 3px;
}
.login-btn{
  height: 50px;
}
.css-z0tt1n {
    position: relative;
    display: flex;
    /* -webkit-box-pack: center; */
    justify-content: center;
    border-top: 1px solid rgba(112, 115, 124, 0.22);
    padding-top: 20px;
}
.or {
    font-weight: var(--text-weight-caption1-bold);
    text-align: left;
    letter-spacing: var(--text-spacing-caption1);
    font-size: var(--text-size-caption1);
    line-height: var(--text-height-caption1);
    position: absolute;
    top: 0px;
    left: 50%;
    transform: translateY(-50%) translateX(-50%);
    background-color: white;
    padding: 0px 12px;
    color: rgba(55, 56, 60, 0.61);
}
</style>
