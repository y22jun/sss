<script setup>
import { onMounted, ref, watch } from "vue";
import { api } from "@/utils/axios"
import { useSessionStore } from "@/stores/session"
import { useRouter } from 'vue-router';
//pinia
const sessionStore = useSessionStore();

const router = useRouter();
const inputemail = ref("");
const username = ref("");
const password = ref("");
const ispassword = ref("");
const certifySuccess = ref(false);
const sendMailBtn = ref("코드 전송");
const inputAuthCode = ref("인증번호를 입력하세요");

const duringCertify = ref(false);
const SendEmail = async () => {
  if(duringCertify.value) return;
  console.log("Sending email");
  duringCertify.value = true;
  let remainingTime = 5 * 60;

  const data = { email : inputemail.value }
  //이메일 전송 -> 전송하면 true값 반환 / 못하면 error와 false값 반환
  try{
    const result = await api.user.email(data);
  } catch (e){
    alert("서버 오류 전송 실패");
    duringCertify.value = false;
    sendMailBtn.value = "재전송";
    return;
  }

  // 5분 타이머 설정
  const interval = setInterval(() => {
    const minutes = Math.floor(remainingTime / 60);
    const seconds = remainingTime % 60;

    const formattedTime = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
    sendMailBtn.value = formattedTime;
    
    remainingTime -= 1; // 남은 시간을 1초씩 감소

    if(certifySuccess.value){
      clearInterval(interval); // 타이머 종료
      sendMailBtn.value = "완료"; // 버튼에 텍스트 변경
    }
    if (remainingTime < 0) {
      clearInterval(interval); // 타이머 종료
      sendMailBtn.value = "재전송"; // 버튼에 텍스트 변경
      duringCertify.value = false; // duringCertify 값을 false로 변경
    }
  }, 1000); // 1초마다 실행
}

const CertifyEmail = async () =>{
  console.log(inputemail.value, inputAuthCode.value);
  const data = {email:inputemail.value, authcode:inputAuthCode.value}
  const result = await api.user.verfication(data);

  certifySuccess.value = result;
}

async function hashPassword(){
    try{
        const encoder = new TextEncoder();
        const data = encoder.encode(password.value);

        const hashBuffer = await crypto.subtle.digest("SHA-256", data);

        const hashedPassword = btoa(String.fromCharCode(...new Uint8Array(hashBuffer)));

        console.log("[HASSPASSWORD] : ", hashedPassword);
    } catch (error){
        console.log("[HASSPASSWORD] : ", error);
    }
}

async function signup(){
    hashPassword();
    //api 호출
    const data = { email : inputemail.value, username : username.value, password : password.value}
    try{
        const res = (await api.user.signup(data));
        if(res) alert("[SIGNUP] : Signup!");
        else alert("[SIGNUP] : Signup Failed!");
    } catch (error){
        alert("[SIGNUP] : Signup Error");
    }
}

// 이메일 유효성
const inputemailIsValid = ref(true);
const validateInputEmail = () => {
    // 이메일 유효성 검사 로직을 여기에 추가
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    inputemailIsValid.value = emailPattern.test(inputemail.value);
};

const inputNameIsValid = ref(true);
const validateInputName = () => {
  const koreanRegex = /^[가-힣]{1,6}$/; // 한글 6자 이내
  const englishRegex = /^[a-zA-Z]{4,10}$/; // 영어 10자 이내

  const isValid = koreanRegex.test(username.value) || englishRegex.test(username.value);
  inputNameIsValid.value = isValid;
  console.log(username.value);
}

const inputPasswordIsValid = ref(true);
const validateInputPassword = () => {
  const passwordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()\-_=+{};:,<.>/?]).{8,16}$/;
  inputPasswordIsValid.value = passwordRegex.test(password.value);
  console.log(password.value);
  console.log(inputPasswordIsValid.value);
}

watch(inputemail,() => {
  validateInputEmail();
})

watch(username, () => {
  validateInputName();
})

watch(password, () => {
  validateInputPassword();
})

</script>
<template>
  <div class="container py-4">
    <div class="row align-items-center justify-content-center">
      <img class="image" src="/logo-white.jpg" alt="sofit-logo" style="justify-self: center;">
    </div>
    <div class="form-group">
      <label for="inputUserName" class="form-label mt-4">이름</label>
      <input
        type="text"
        class="form-control"
        id="inputUserName invaild"
        v-model="username"
        aria-describedby="nameHelp"
        :class="!inputNameIsValid ? 'is-invalid' : username === '' ? '' : 'is-valid'"
      />
      <div v-if="!inputNameIsValid" class="invalid-feedback">올바른 이름 형식을 입력하세요 ( 한글 6자 이내, 영어 4자 이상, 10자 이내)</div>
      <div v-else class="valid-feedback">Correct</div>
    </div>
    <!-- 이메일 -->
    <div class="form-group">
      <label for="InputEmail" class="form-label mt-4">아이디(이메일)</label>
      <input
        type="text"
        class="form-control"
        id="InputEmail"
        v-model="inputemail"
        aria-describedbys="emailHelp"
        :class="!inputemailIsValid ? 'is-invalid' : inputemail === '' ? '' : 'is-valid'"
        :disabled="duringCertify || certifySuccess"
      />
      <div v-if="!inputemailIsValid" class="invalid-feedback">올바른 이메일 형식을 입력하세요</div>
      <div v-else class="valid-feedback">Correct</div>

      <!-- 이메일 인증 폼 -->
      <div class="form-group mt-3 email-form" :class="{'email-certification' : inputemailIsValid && !(inputemail === '')}">
        <label for="InputEmail" class="form-label mt-1 pr-3 ">인증 번호 :</label>
        <input type="text" class="form-control mr-3 " :placeholder="inputAuthCode" v-model="inputAuthCode" :disabled="!duringCertify">
        <button class="btn btn-primary authbutton" @click="SendEmail()" :disabled="duringCertify || certifySuccess"> {{ sendMailBtn }} </button>
        <button class="btn btn-primary authbutton  mr-3" @click="CertifyEmail()" :disabled="!duringCertify || certifySuccess"> 인증 </button>
        <div v-if="!certifySuccess && duringCertify" class="invalid-feedback">인증번호가 틀렸습니다.</div>
        <div v-else class="valid-feedback">Correct</div>
      </div>

    </div>
    <!-- 비밀번호 -->
    <div class="form-group" :class="!inputPasswordIsValid ? 'has-danger' : 'has-success' ">
        <label for="inputValid" class="form-label mt-4">비밀번호</label>
        <!-- <button class="btn">보기</button> -->
        <input type="password" :disabled="!certifySuccess" class="form-control" v-model="password" :class="!inputPasswordIsValid ? 'is-invalid' : password === '' ? '' :'is-valid'">
        <div v-if="!inputPasswordIsValid" class="invalid-feedback">비밀번호는 영문 소문자, 대문자, 특수 문자(!@#$%^) 중 최소 2개를 포함하여 8자 이상, 16자 이하여야 합니다.</div>
        <div v-else class="valid-feedback">Correct</div>
      </div>
    
    <div class="form-group">
        <label for="inputSuccess" class="form-label mt-4">비밀번호 확인</label>
        <input type="password" :disabled="!certifySuccess" class="form-control" v-model="ispassword" :class="!(ispassword === password) ? 'is-invalid' :  ispassword === '' ? '' :  'is-valid'">
        <div v-if="!(ispassword === password)" class="invalid-feedback">비밀번호가 같지 않습니다.</div>
        <div v-else class="valid-feedback">Correct</div>
    </div>

    <div class="d-grid gap-2 pt-5">
        <button class="btn btn-primary btn-lg" type="button" @click="signup()">Register</button>
    </div>
  </div>
</template>

<style scoped>
.image{
  width: 300px;
  height: 70px;
}
.container{
  width:60vh;
}
.invalid-feedback,
.valid-feedback{
  margin-left: 3px;
}
.email-form{
  height: 0;
  visibility: hidden;
  display: flex;
  flex-direction: row;
  justify-content: end;
}
.email-certification{
  height: 30px;
  visibility:visible;
  transition: height 1s;
}
.email-form > .form-control{
  width:300px;
  height: 35px;
  margin-left: 5px;
}

.feedback {
    color: #dc3545; /* 유효성 검사 실패 시 메시지의 글자 색상 */
    font-size: 14px; /* 유효성 검사 실패 시 메시지의 글자 크기 */
    margin-top: .25rem; /* 유효성 검사 실패 시 메시지의 위쪽 여백 */
}

.authbutton{
  margin-left: 5px;
  height: 35px;
  text-align: center;
}
</style>
