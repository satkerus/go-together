import React from 'react';
import {Card, CardBody, CardLink, CardSubtitle, CardText, CardTitle} from "reactstrap";
import Gallery from "../Galery";
import PropTypes from "prop-types";
import {getSrcForImg} from "../../utils";
import {Event} from "../../../types";
import FormReference from "../FormReference";
import DeleteButton from "../DeleteButton/DeleteButton";

const ItemEvent = ({event, onClickChooseApartment, onDelete}) =>
    <Card body style={{align: 'center'}}>
        <DeleteButton onDelete={() => onDelete(event.id)}/>
        <img className='fixed-width-main-image-card' src={getSrcForImg(event.eventPhotoDto.photos[0])} alt=""/>
        <CardBody>
            <CardTitle>{event.name}</CardTitle>
            <CardSubtitle>{event.description}</CardSubtitle>
            <CardSubtitle>I {event.author.firstName}, {event.author.lastName}</CardSubtitle>
            <CardSubtitle>From {event.author.location.name}, {event.author.location.country.name}</CardSubtitle>
            <CardSubtitle>Languages: {event.author.languages.map(lang => lang.name).join(', ')}</CardSubtitle>
            <CardSubtitle>My
                interests: {event.author.interests.map(interest => interest.name).join(', ')}</CardSubtitle>
            <CardSubtitle>Going to travel through {event.route.map(location => location.location.name + ", " +
                location.location.country.name).join(" -> ")}</CardSubtitle>
            <CardText>With {event.peopleCount} friends</CardText>
            <CardText>And I found {event.users.length} friends</CardText>
            <CardText>{event.peopleLike} people liked this trip</CardText>
            <CardText>Live by {event.housingType}</CardText>
            <FormReference formRef={'/events/' + event.id}
                           action={() => onClickChooseApartment(event)}
                           description='Choose event'/>
            {event.eventPhotoDto.photos.length !== 0 ?
                <CardLink><Gallery
                    images={event.eventPhotoDto.photos.map(photo => getSrcForImg(photo))}
                    showThumbnails={true}
                /></CardLink>
                : null}
        </CardBody>
    </Card>;

ItemEvent.propTypes = {
    event: Event.isRequired,
    onClickChooseApartment: PropTypes.func.isRequired,
    onDelete: PropTypes.func
};

export default ItemEvent;