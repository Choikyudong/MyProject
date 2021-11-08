import axios from "axios";

const NormalAxios = axios.create({
    baseURL: "http://localhost:8080/api",
    responseType: "json",
    headers: {
        "Content-type": "application/json",
    }
});

const AuthAxios = axios.create({
    baseURL: "http://localhost:8080/api",
    responseType: "json",
    headers: {
        "Content-type": "application/json",
    }
});

function getToeknValue() {
    const getItem = localStorage.getItem("user");
    let accessToken = null;
    if (getItem) {
        accessToken = JSON.parse(getItem);
    }
    return accessToken.token;
}

function getRefreshValue() {
    const getItem = localStorage.getItem("user");
    let refreshToken = null;
    if (getItem) {
        refreshToken = JSON.parse(getItem);
    }
    return refreshToken.refreshToken;
}

const updateRefreshToken = (token:any) => {
    let user = JSON.parse(localStorage.getItem("user") || "");
    user.token = token;
    localStorage.setItem("user", JSON.stringify(user));
}

AuthAxios.interceptors.request.use(
    (config:any) => {
        const token = getToeknValue();
        if (token) {
            config.headers.authorization = "Bearer " + token;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
)

AuthAxios.interceptors.response.use(
    (responseData) => {
        return responseData;
    },
    async (error) => {
        const originalConfig = error.config;
        if (originalConfig.url !== "/refresh" && error.response) {
            console.log(originalConfig);
            if (error.response.status === 401 && !originalConfig._retry) {
                originalConfig._retry = true;

                try {
                    const reissue = await NormalAxios.post("/refresh", {
                        refreshToken: getRefreshValue()
                    })
                    const { token }:any = reissue.data;
                    updateRefreshToken(token);
                    return AuthAxios(originalConfig);
                } catch (error) {
                    return Promise.reject(error);
                }
            }
        }
        return Promise.reject(error);
    }
);


export default {
    NormalAxios,
    AuthAxios
}
