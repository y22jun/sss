import { ref, reactive } from "vue";
import { defineStore } from "pinia";
import { api } from "@/utils/axios";
import { useRouter } from "vue-router";
import { useCookies } from "vue3-cookies";

export const useSessionStore = defineStore("session", () => {
  const {cookies} = useCookies();
  const router = useRouter();
  const state = reactive({
    isLoggedIn: false,
  });

  //토큰을 스토리지에 저장
  async function login(email, token) {
    const result = await api.user.logincheck(email, token);
    console.log("[SESSION] : ", result);
    if (result) {
      cookies.set("email", email, 60 * 60 * 4);
      cookies.set("token", token, 60 * 60 * 4);
      console.log("[COOKIES] SET email : ", email);
      console.log("[COOKIES] SET token : ", token);
      state.isLoggedIn = true;
      return true;
    }
    else {
      cookies.remove("email");
      cookies.remove("token");
      state.isLoggedIn = false;
      return false;
    }
  }

  async function user_logout(email, token) {
    const result = await api.user.logout(email, token) 
    cookies.remove("email");
    cookies.remove("token");
    state.isLoggedIn = false;
  }

  
  async function session() {
    const email = cookies.get("email");
    const token = cookies.get("token");
    console.log("[COOKIES] GET email : ", email);
    console.log("[COOKIES] GET token : ", token);

    if(email && token){
      const result = await api.user.logincheck(email, token);
      console.log("[SESSION] : ", result);
      state.isLoggedIn = result;
      return;
    }
    
    //쿠키에 세션이 없음
    else {
      console.log("[SESSION] : ", false);
      state.isLoggedIn = false;
      router.push({ name : "home"});
      return;
    }
  }

  return { state, login, user_logout, session, cookies };
});
