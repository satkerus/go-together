import React, {useEffect, useState} from "react";
import {connect} from "../../Context";
import ViewEvent from "./ViewEvent";
import PropTypes from 'prop-types';
import moment from "moment";
import {FORM_ID} from "./constants";
import {getEvent} from "./actions";
import {Event} from "../../types";

const GetAndViewEvent = ({id, getEvent, event}) => {
    const [currentEvent, setCurrentEvent] = useState(null);

    useEffect(() => {
        getEvent(id);
    }, [getEvent, id]);

    useEffect(() => {
        if (event) {
            const result = {...event};
            result.startDate = moment(event.startDate);
            result.endDate = moment(event.endDate);
            setCurrentEvent(result)
        }
    }, [setCurrentEvent, event]);

    return currentEvent && <ViewEvent event={currentEvent}/>;
};

GetAndViewEvent.propTypes = {
    id: PropTypes.string.isRequired,
    getEvent: PropTypes.func.isRequired,
    event: Event
};

const mapStateToProps = state => ({
    event: state[FORM_ID]?.event,
});

export default connect(mapStateToProps, {getEvent}, FORM_ID)(GetAndViewEvent);