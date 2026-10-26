export type TaskStatus = 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';

export interface User {
  id: number;
  name: string;
  surname: string;
  email: string;
  phone?: string;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  surname: string;
  email: string;
  phone?: string;
  password: string; // mínimo 6 caracteres
}

export interface Category {
  id: number;
  name: string;
  default: boolean;
}

export interface CategoryRequest {
  name: string;
}

export interface Task {
  id: number;
  name: string;
  description?: string;
  status: TaskStatus;
  date: string; // formato yyyy-MM-dd
  endDate?: string;
  categories: Category[];
}

export interface TaskRequest {
  name: string;
  description?: string;
  date: string;
  endDate?: string;
  categoryIds?: number[];
}