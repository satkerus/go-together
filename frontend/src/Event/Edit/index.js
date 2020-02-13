import React, {useContext, useEffect, useState} from 'react';
import {Context} from "../../Context";
import {DEFAULT_CREATE_EVENT, EVENT_SERVICE_URL} from "../../utils/constants";
import * as PropTypes from "prop-types";
import ViewEvent from "./ViewEvent";
import moment from 'moment';

const CreateEvent = ({id}) => {
    const [event, setEvent] = useState(null);

    const [state] = useContext(Context);

    useEffect(() => {
        if (id) {
            state.fetchWithToken(EVENT_SERVICE_URL + '/events/' + id, event => {
                event.startDate = moment(event.startDate);
                event.endDate = moment(event.endDate);
                setEvent(event);
            })
        }
    }, [id, state]);

    return ((id && event) || !id) && <ViewEvent event={id ? event : {...DEFAULT_CREATE_EVENT}}/>
};

CreateEvent.propTypes = {
    id: PropTypes.string
};

export default CreateEvent;