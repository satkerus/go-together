import React, {useEffect} from "react";
import createEventLikes from "../../../../utils/components/Event/EventLikes";
import ParticipationButton from "../../ParticipationButton";
import {connect} from "../../../../../App/Context";
import * as PropTypes from "prop-types";
import {Event, ResponseData} from "../../../../utils/types";
import LeftContainer from "../../../../utils/components/Container/LeftContainer";
import ItemContainer from "../../../../utils/components/Container/ItemContainer";
import {FORM_ID} from "../../constants";
import moment from "moment";
import LoadableContent from "../../../../utils/components/LoadableContent";
import {postLikes} from "../../../../utils/components/Event/EventLikes/actions";

const EventLikes = createEventLikes(FORM_ID);

const CommonInfo = ({event, users, setRefresh, userId, postLikes}) => {
    useEffect(() => {
        postLikes([event.id]);
    }, [event, postLikes]);

    return <LeftContainer style={{width: '600px'}}>
        <ItemContainer>
            <h4>{event.name}</h4>
        </ItemContainer>
        <ItemContainer>
            {userId && userId !== event.author.id && <EventLikes eventId={event.id} eventIds={[event.id]}/>}
        </ItemContainer>
        <ItemContainer>
            {userId && userId !== event.author.id &&
            <LoadableContent loadableData={users}>
                <ParticipationButton users={users.response}
                                     setRefresh={setRefresh}
                                     eventId={event.id}/>
            </LoadableContent>
            }
        </ItemContainer>
        <ItemContainer>
            <h5>About</h5>
        </ItemContainer>
        <ItemContainer>
            <div dangerouslySetInnerHTML={{__html: event.description}}/>
        </ItemContainer>
        <ItemContainer>
            <h5>Event paid things:</h5>
        </ItemContainer>
        <ItemContainer>
            {event.paidThings.map((p, key) => {
                return (<p key={key}>• {p.cashCategory} - {p.paidThing.name}</p>
                )
            })}
        </ItemContainer>
        <ItemContainer>
            Trip dates: {moment(event.startDate).format('LLL')} -> {moment(event.endDate).format('LLL')}
        </ItemContainer>
    </LeftContainer>
};

CommonInfo.propTypes = {
    event: Event.isRequired,
    users: ResponseData.isRequired,
    setRefresh: PropTypes.func.isRequired,
    userId: PropTypes.string
};

const mapStateToProps = () => state => ({
    userId: state.userId
});

export default connect(mapStateToProps, {postLikes})(CommonInfo)(FORM_ID);