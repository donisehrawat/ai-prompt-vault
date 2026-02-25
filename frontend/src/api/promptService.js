export const getPrompts = async (authAxios) => {
    const response = await authAxios.get('/prompt/view-prompts');
    return response.data;
};

export const createPrompt = async (authAxios, data) => {
    const response = await authAxios.post('/prompt/create', data);
    return response.data;
};

export const editPrompt = async (authAxios, id, data) => {
    const response = await authAxios.patch(`/prompt/editPrompt/${id}`, data);
    return response.data;
};

export const deletePrompt = async (authAxios, id) => {
    const response = await authAxios.delete(`/prompt/deletePrompt/${id}`);
    return response.data;
};
