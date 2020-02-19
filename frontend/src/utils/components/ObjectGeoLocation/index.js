import React, {useEffect, useState} from "react";
import PropTypes from "prop-types";
import GoogleMapReact from 'google-map-react';

import "./style_place.css";
import AddressFields from "./AddressFields";
import {fetchAndSet} from "../../api/request";
import Marker from "./Marker";
import {getAddress, getCity, getCountry, getState} from "./utils";

const GOOGLE_API_KEY = "AIzaSyBSjnMkN8ckymUWZO5v0q-cZW9WppoFsyM";
const getMapOptions = () => {
    return {
        disableDefaultUI: true,
        mapTypeControl: true,
        streetViewControl: true,
        styles: [{featureType: 'poi', elementType: 'labels', stylers: [{visibility: 'on'}]}],
    };
};

const ObjectGeoLocation = ({isViewedAddress, routes, draggable, onChange, zoom, setCurrentCenter}) => {
    const [center, setCenter] = useState([18.5204, 73.8567]);
    const [isDraggable, setIsDraggable] = useState(true);
    const [zoomValue, setZoomValue] = useState(zoom || 9);
    const [currentKey, setCurrentKey] = useState(0);
    const [currentLat, setCurrentLat] = useState(center[0]);
    const [currentLng, setCurrentLng] = useState(center[1]);
    const [response, setResponse] = useState(null);
    const [googleMap, setGoogleMap] = useState(null);
    const [polyline, setPolyline] = useState(null);
    const [lock, setLock] = useState(false);

    const URL_FROM_LAN_LNG_TO_LOCATION = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${currentLat},${currentLng}
    &key=${GOOGLE_API_KEY}&language=en`;


    useEffect(() => {
        routes.filter(route => !route.address)
            .forEach(route => {
                const URL_FROM_LAN_LNG_TO_LOCATION_CUSTOM = `https://maps.googleapis.com/maps/api/geocode/json?latlng=${route.latitude},
                ${route.longitude}&key=${GOOGLE_API_KEY}&language=en`;
                fetchAndSet(URL_FROM_LAN_LNG_TO_LOCATION_CUSTOM, (result) => {
                    setResponse(result);
                    setLock(false);
                    onChange(route.routeNumber, ['location.name', 'location.country.name', 'location.state', 'address'],
                        [getCity(result), getCountry(result), getState(result), getAddress(result)]);
                })
            })
    }, [routes, onChange]);
    useEffect(() => {
        const getCurrentRoute = routes.filter(route => route.routeNumber === currentKey)[0];
        if (routes.length !== 0 && currentKey && getCurrentRoute.latitude && getCurrentRoute.longitude &&
            (getCurrentRoute.latitude !== currentLat && currentLat !== 18.5204) &&
            (getCurrentRoute.longitude !== currentLng && currentLng !== 73.8567)
            && /*!response && */draggable) {
            //fetchAndSet(URL_FROM_LAN_LNG_TO_LOCATION, setResponse);
            onChange(currentKey, ['latitude', 'longitude'], [currentLat, currentLng]);
        }
    }, [routes, currentKey, response, draggable, URL_FROM_LAN_LNG_TO_LOCATION, onChange, currentLat, currentLng]);

    const onCircleInteraction = (childKey, childProps, mouse) => {
        // function is just a stub to test callbacks
        setIsDraggable(false);
        setCurrentKey(parseInt(childKey));
        setCurrentLat(mouse.lat);
        setCurrentLng(mouse.lng);
        setLock(false)
    };

    const onCircleInteraction3 = (childKey, childProps, mouse) => {
        setIsDraggable(true);
        setCurrentKey(parseInt(childKey));
        setCurrentLat(mouse.lat);
        setCurrentLng(mouse.lng);
    };

    const _onChange = ({center, zoom}) => {
        setCenter(center);
        setCurrentCenter(center);
        setZoomValue(zoom);
        setLock(false)
    };

    const endDrag = () => {
        fetchAndSet(URL_FROM_LAN_LNG_TO_LOCATION, (result) => {
            setResponse(result);
            setLock(false);
            onChange(currentKey, ['location.name', 'location.country.name', 'location.state', 'address'],
                [getCity(result), getCountry(result), getState(result), getAddress(result)]);
        });
    };

    const handleGoogleMapApi = (google) => {
        if (google) {
            setGoogleMap({map: google.map, maps: google.maps});
            handlePolyline(google);
        }
    };

    const handlePolyline = (google) => {
        const o = googleMap || google;

        if (!lock) {
            if (polyline) {
                polyline.setMap();
            }
            const flightPath = new o.maps.Polyline({
                path: routes.map(route => ({lat: route.latitude, lng: route.longitude})),
                geodesic: true,
                strokeColor: '#33BD4E',
                strokeOpacity: 1,
                strokeWeight: 5
            });

            flightPath.setMap(o.map);
            setPolyline(flightPath);
            setLock(true)
        }
    };

    const getRoutes = () => routes.map(route => <Marker
        key={route.routeNumber}
        lat={route.latitude}
        lng={route.longitude}
        name={route.routeNumber}
        onRightClick={() => console.log('dddd')}
        color={getColorByRouteNumber(route.routeNumber)}
    />);

    const getColorByRouteNumber = (routeNumber) => {
        if (routeNumber === 1) {
            return 'green';
        } else if (routeNumber === routes.length) {
            return 'orange'
        } else {
            return 'red';
        }
    };

    return <div className='flex'>
        {isViewedAddress && response && response.results[0] && <AddressFields response={response}
                                                                              onChange={onChange}/>}
        <div style={{width: '100%', height: 400}}>
            <GoogleMapReact bootstrapURLKeys={{key: GOOGLE_API_KEY}}
                            yesIWantToUseGoogleMapApiInternals
                            onGoogleApiLoaded={handleGoogleMapApi}
                            draggable={isDraggable}
                            onChange={_onChange}
                            center={center}
                            zoom={zoomValue}
                            options={getMapOptions}
                            onChildMouseDown={draggable ? onCircleInteraction : () => null}
                            onChildMouseUp={draggable ? onCircleInteraction3 : () => null}
                            onChildMouseMove={draggable ? onCircleInteraction : () => null}
                            onChildClick={draggable ? endDrag : () => null}
                            onClick={() => null}
            >
                {googleMap && handlePolyline(googleMap)}
                {getRoutes()}
            </GoogleMapReact>
        </div>
    </div>;
};

ObjectGeoLocation.props = {
    isViewedAddress: PropTypes.bool.isRequired,
    setCurrentCenter: PropTypes.func,
    routes: PropTypes.array,
    draggable: PropTypes.bool.isRequired,
    onChange: PropTypes.func,
    zoom: PropTypes.number
};

export default ObjectGeoLocation;