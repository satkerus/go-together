import {USER_SERVICE_URL} from "forms/utils/constants";
import {
    CREATED_USER,
    FORM_ID,
    REGISTER_ALL_INTERESTS,
    REGISTER_ALL_LANGUAGES,
    REGISTER_CHECK_MAIL,
    REGISTER_CHECK_USERNAME
} from "./constants";
import {PUT} from "App/utils/api/constants";

const URL_USERS_LANGUAGES = USER_SERVICE_URL + "/languages";
const URL_USERS_INTERESTS = USER_SERVICE_URL + "/interests";

export const getAllLanguages = () => (dispatch) => {
    dispatch({
        type: REGISTER_ALL_LANGUAGES,
        url: URL_USERS_LANGUAGES
    });
};

export const getAllInterests = () => (dispatch) => {
    dispatch({
        type: REGISTER_ALL_INTERESTS,
        url: URL_USERS_INTERESTS
    });
};

export const getCheckMail = (value) => (dispatch) => {
    dispatch({
        type: REGISTER_CHECK_MAIL,
        url: USER_SERVICE_URL + '/users/check/mail/' + value
    });
};

export const getCheckUserName = (value) => (dispatch) => {
    dispatch({
        type: REGISTER_CHECK_USERNAME,
        url: USER_SERVICE_URL + '/users/check/login/' + value
    });
};

export const regUser = () => (dispatch, state) => {
    const location = {
        ...state.components.utils.input[FORM_ID].value.location,
        category: 'USER',
        locations: [{
            ...state.components.utils.input[FORM_ID].value.location.locations[0],
            routeNumber: 1,
            isStart: true,
            isEnd: false
        }]
    }

    dispatch({
        type: CREATED_USER,
        url: USER_SERVICE_URL + "/users",
        method: PUT,
        data: {...state.components.utils.input[FORM_ID].value, location}
    });
};