
import axios from "axios";

// BOJ
const solvedacURL = "https://solved.ac/api/v3";

const DOMAIN = "http://localhost:8080";
const request = axios.create({
  baseURL: `${DOMAIN}/api`,
});

export const api = {
  // solved
  user: {
    login: async (id, pw) => {
      try{
        const data = {"email" : id, "password" : pw};
        const result = await axios.post(`${DOMAIN}/api/member/login`,data);
        return result.data
      
      } catch (error) {
        console.log(error);
        throw new Error("로그인 실패");
      }
    },
    logincheck: async (email, token) => {
      const data =  {"email" : email, "token" : token};
      const result = await axios.post(`${DOMAIN}/api/member/loginCheck`,data);
      return result.data
    },
    logout: async(email, token) => {
      try{
        const data = {"email" : email, "token" : token};
        await axios.post(`${DOMAIN}/api/member/logout`,data);
      } catch (error) {
        console.log(error);
      }
    },
    signup: async (data) => {
      try{
        const result = await axios.post(`${DOMAIN}/api/member/signUp`,data);
        return result.data;
      } catch (error) {
        console.log(error);
        throw new Error("회원가입 실패");
      }
    },
    email: async (data) => {
      try{
        const result = await axios.post(`${DOMAIN}/api/member/emails/verification-requests`,data);
        return result.data;
      } catch (error) {
        console.log(error);
        throw new Error("이메일 전송 실패");
      }
    },
    verfication: async (data) => {
      try{
        const result = await axios.post(`${DOMAIN}/api/member/emaills/verfications`, data);
        return result.data;
      } catch (error) {
        console.log(error);
        throw new Error("인증 실패");
      }
    },
    getGroup: async (data) => {
      try{
        const result = await axios.post(`${DOMAIN}/api/member/getGroup`, data);
        return result.data;
      } catch (error) {
        console.log(error);
        throw new Error("그룹 없음");
      }
    }
  },
  group : {
    create : async (data) => {
        const result = await axios.post(`${DOMAIN}/api/groups/create`, data);
        return result;
    },
    getAll : async (data) => {
      const result = await axios.post(`${DOMAIN}/api/groups/getAll`, data);
      return result.data;
    },
    getOne : async (data) => {
      const result = await axios.post(`${DOMAIN}/api/groups/getOne`, data);
      return result.data;
    },
    getById : async (data) => {
      const result = await axios.post(`${DOMAIN}/api/groups/getById`, data);
      return result.data;
    },
    getRole : async (data) => {
      const result = await axios.post(`${DOMAIN}/api/groups/getRole`, data);
      return result.data;
    },
    addGroupProblem : async (data) => {
      const result = await axios.post(`${DOMAIN}/api/problem/addGroupProblem`, data);
      return result.data;
    },
    register : async (data) => {
      const result = await axios.post(`${DOMAIN}/api/groups/register`, data);
      return result.data;
    },
    getWeekProblem: async (data) => {
      const result = await axios.post(`${DOMAIN}/api/groups/getWeekProblem`, data);
      return result.data;
    },
  },
  baekjoon:{
    search: async (id) => {
      const result = await axios.post(`${DOMAIN}/api/Baekjoon/search`, id);
      return result.data;
    }
  }
};
