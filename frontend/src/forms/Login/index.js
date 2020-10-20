import React, {useEffect, useState} from 'react';
import PropTypes from "prop-types";
import MenuItem from "@material-ui/core/MenuItem";
import {connect} from "App/Context";
import {navigate} from "hookrouter";

import LabeledInput from "forms/utils/components/LabeledInput";
import ItemContainer from "forms/utils/components/Container/ItemContainer";
import ContainerColumn from "forms/utils/components/Container/ContainerColumn";
import CustomButton from "forms/utils/components/CustomButton";

import {getLoginId, postLogin, setAuth} from "./actions";
import './style.css'

const Login = ({
                   formId, postLogin, handleMenuClose, loginId, getLoginId,
                   setAuth, loginToken
               }) => {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');

    useEffect(() => {
        if (loginToken.token && !loginToken.inProcess && login && login !== "") {
            getLoginId(login);
        }
    }, [loginToken, getLoginId, login]);

    useEffect(() => {
        if (loginId.response.id) {
            setAuth(loginId.response.id, loginToken.token);
        }
    }, [loginId, setAuth]);

    const handleSubmit = () => {
        postLogin(login, password);
    };

    return <ContainerColumn>
        <MenuItem button={false}
                  component={formId + 'login'}>
            <ItemContainer>
                <LabeledInput
                    id="login"
                    label="Login"
                    value={login}
                    onChange={setLogin}
                />
            </ItemContainer>
        </MenuItem>
        <MenuItem button={false}
                  component={formId + 'login'}>
            <ItemContainer>
                <LabeledInput
                    type='password'
                    id="password"
                    label="Password"
                    value={password}
                    onChange={setPassword}
                />
            </ItemContainer>
        </MenuItem>
        <MenuItem button={true}
                  component={formId + 'login'}>
            <ItemContainer>
                <CustomButton text='Login'
                              onClick={handleSubmit}/>
            </ItemContainer>
        </MenuItem>
        <MenuItem button={false}
                  component={formId + 'login'}>
            <ItemContainer>
                <CustomButton text='Sign up'
                              onClick={() => {
                                  navigate('/register');
                                  handleMenuClose();
                              }}/>
            </ItemContainer>
        </MenuItem
        >
    </ContainerColumn>;
};

Login.propTypes = {
    formId: PropTypes.string.isRequired,
    postLogin: PropTypes.func.isRequired,
    handleMenuClose: PropTypes.func.isRequired
};

const mapStateToProps = state => ({
    loginId: state.components.forms.login.loginId,
    loginToken: state.components.forms.login.loginToken,
});

export default connect(mapStateToProps,
    {postLogin, getLoginId, setAuth})(Login);