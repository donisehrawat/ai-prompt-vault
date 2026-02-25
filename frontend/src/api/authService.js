import { publicAxios } from './axiosInstance';

export const signUp = async (username, password) => {
    const response = await publicAxios.post('/public/sign-up', { username, password });
    return response.data;
};
