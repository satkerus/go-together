import React from "react";
import {connect} from "../../Context";
import PropTypes from "prop-types";
import CreateButton from "../Buttons/CreateButton";
import NotificationButton from "../Buttons/NotificationButton";
import AccountButton from "../Buttons/AccountButton";
import LogoutButton from "../Buttons/LogoutButton";
import FormLogin from "../../../forms/Login";

const ToolbarButtons = ({userId, menuId, handleMenuClose}) => {
    return userId !== null ? [
        <CreateButton menuId={menuId}/>,
        <NotificationButton menuId={menuId}/>,
        <AccountButton menuId={menuId}/>,
        <LogoutButton menuId={menuId}/>
    ] : <FormLogin formId={menuId} handleMenuClose={handleMenuClose}/>
};

ToolbarButtons.propTypes = {
    userId: PropTypes.string,
    menuId: PropTypes.string.isRequired,
    handleMenuClose: PropTypes.func.isRequired
};

const mapStateToProps = () => (state) => ({
    userId: state.userId.value
});

export default connect(mapStateToProps, null)(ToolbarButtons);