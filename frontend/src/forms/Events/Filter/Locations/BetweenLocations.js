import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";

import AutocompleteLocation from "forms/utils/components/AutocompleteLocation";
import CustomButton from "forms/utils/components/CustomButton";
import DeleteIcon from "forms/utils/components/Icon/Delete";
import ItemContainer from "forms/utils/components/Container/ItemContainer";
import Container from "forms/utils/components/Container/ContainerRow";
import {connect} from "App/Context";

import {getChangedRoutes} from "./index";

const BetweenLocations = ({onChangeLocation, filters}) => {
    const [routes, setRoutes] = useState([]);

    useEffect(() => {
        if (filters?.filters) {
            const newRoutes = getChangedRoutes(filters, routes, (value) => !value.isEnd && !value.isStart);
            setRoutes(newRoutes);
        }
    }, [filters]);

    const onDeleteLocation = (index) => () => {
        const updatedRoutes = routes
            .filter(route => parseInt(route.number) !== parseInt(index))
            .map(route => {
                if (parseInt(route.number) > parseInt(index)) {
                    const updatedRoute = {...route};
                    updatedRoute.number = route.number - 1;
                    return updatedRoute;
                }
                return route;
            });
        setRoutes(updatedRoutes);
        onChangeLocation(updatedRoutes);
    }

    const updateLocation = (index) => ({value, lat, lng}) => {
        const updatedLocation = routes.map(route => {
            if (route.number === index) {
                const updatedLocation = {...route};
                updatedLocation.value = value;
                updatedLocation.lat = lat;
                updatedLocation.lng = lng;
                return updatedLocation;
            }
            return route;
        });
        setRoutes(updatedLocation);

        if (lat && lng) {
            onChangeLocation(updatedLocation);
        }
    };

    const onAddLocation = () => {
        const newLocation = {
            number: routes.length + 1
        };
        setRoutes([...routes, newLocation]);
    }
    return <Container>
        {routes.map(route => <ItemContainer>
            <AutocompleteLocation key={route.number}
                                  value={route.value}
                                  setValueCenter={updateLocation(route.number)}
                                  placeholder='Middle place'
            />
            <DeleteIcon onDelete={onDeleteLocation(route.number)}/>
        </ItemContainer>)}
        <ItemContainer>
            <CustomButton onClick={onAddLocation}
                          text='Add place'/>
        </ItemContainer>
    </Container>
}

BetweenLocations.propTypes = {
    onChangeLocation: PropTypes.func.isRequired
}

const mapStateToProps = state => ({
    filters: state.components.forms.events.filter.response
});

export default connect(mapStateToProps, null)(BetweenLocations);