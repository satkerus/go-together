import React from 'react';
import PropTypes from "prop-types";
import {Card, CardActionArea, CardActions, CardContent, CardMedia, Typography} from "@material-ui/core";

import Gallery from "forms/utils/components/Galery";
import {getSrcForImg} from "forms/utils/utils";
import {Event} from "forms/utils/types";
import DeleteIcon from "forms/utils/components/Icon/Delete";
import {PHOTO_OBJECT} from "forms/utils/constants";
import EventLikes from "forms/utils/components/Event/EventLikes";
import {connect} from "App/Context";

const ItemEvent = ({event, onDelete, userId, eventIds}) => {
    return <Card>
        <CardActionArea href={'/events/' + event.id}>
            <DeleteIcon onDelete={() => onDelete(event.id)}/>
            <CardMedia
                component="img"
                height="250"
                image={getSrcForImg(event.eventPhotoDto.photos[0] || {...PHOTO_OBJECT})}
            />
            <CardContent>
                <Typography>{event.name}</Typography>
                <Typography>{event.description}</Typography>
                <Typography>I {event.author.firstName}, {event.author.lastName}</Typography>
                <Typography>From {event.author.location.name}, {event.author.location.country.name}</Typography>
                <Typography>Languages: {event.author.languages.map(lang => lang.name).join(', ')}</Typography>
                <Typography>My
                    interests: {event.author.interests.map(interest => interest.name).join(', ')}</Typography>
                <Typography>Going to travel through {event.route.map(location => location.location.name + ", " +
                    location.location.country.name).join(" -> ")}</Typography>
                <Typography>With {event.peopleCount} friends</Typography>
                <Typography>Live by {event.housingType}</Typography>
            </CardContent>
        </CardActionArea>
        <CardActions>
            {userId && userId !== event.author.id &&
            <EventLikes eventId={event.id} eventIds={eventIds}/>}
            {event.eventPhotoDto.photos.length !== 0 &&
            <Gallery
                images={event.eventPhotoDto.photos.map(photo => getSrcForImg(photo))}
                showThumbnails={true}
            />}
        </CardActions>
    </Card>;
};

ItemEvent.propTypes = {
    event: Event.isRequired,
    onDelete: PropTypes.func,
    userId: PropTypes.string,
    eventIds: PropTypes.arrayOf(PropTypes.string).isRequired
};

const mapStateToProps = () => (state) => ({
    userId: state.userId.value
});

export default connect(mapStateToProps, null)(ItemEvent);