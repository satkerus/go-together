import React from "react";
import GroupItems from "forms/utils/components/CardItems";
import ImageSelector from "forms/utils/components/ImageSelector";
import {Event} from "forms/utils/types";
import PropTypes from "prop-types";
import ItemContainer from "forms/utils/components/Container/ItemContainer";
import {updateEvent} from "../../actions";
import {connect} from "App/Context";

const Photos = ({event, updateEvent}) => {
    return <>
        <ItemContainer>
            <GroupItems items={event.groupPhoto.photos}
                        isPhotos
                        onDelete={(id) => console.log('delete: ', id)}/>
        </ItemContainer>
        <ItemContainer>
            <ImageSelector
                photos={event.groupPhoto.photos}
                setPhotos={(photos) => updateEvent('groupPhoto.photos', photos)}
                multiple={true}
            /></ItemContainer></>
};

Photos.propTypes = {
    event: Event.isRequired,
    updateEvent: PropTypes.func.isRequired
};

const mapStateToProps = (state) => ({
    event: state.components.forms.event.eventEdit.event.response
});

export default connect(mapStateToProps, {updateEvent})(Photos);