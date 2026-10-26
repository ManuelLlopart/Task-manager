import axios from 'axios';
import { API_URL } from './config';
import { tokenStorage } from './tokenStorage';

export const api = axios.create({
  baseURL: API_URL,
  timeout: 60000, // Render puede tardar en despertar
  headers: { 'Content-Type': 'application/json' },
});

api.interceptors.request.use(async (config) => {
  const isAuthRoute = config.url?.startsWith('/api/auth/');
  if (!isAuthRoute) {
    const token = await tokenStorage.get();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});