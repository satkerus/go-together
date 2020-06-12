import React from 'react';

import {connect} from "App/Context";

import {FilterOperator} from "forms/utils/utils";
import CheckInOutDates from "forms/utils/components/CheckInOutDates";

import {setFilter} from "../actions";

const Dates = ({filter, setFilter}) => {
    const onChangeDate = (searchField, filterOperation) => (date) => {
        let values = null;
        if (date) {
            values = [{
                [searchField]: date
            }];
            setFilter(filterOperation, values, searchField);
        }
    }

    const startDate = filter.filters['startDate'];
    const endDate = filter.filters['endDate'];

    return <CheckInOutDates startDate={startDate && startDate.values[0]?.startDate}
                            endDate={endDate && endDate.values[0]?.endDate}
                            setStartDate={onChangeDate('startDate', FilterOperator.START_DATE)}
                            setEndDate={onChangeDate('endDate', FilterOperator.END_DATE)}/>;
};

const mapStateToProps = () => state => ({
    filter: state.components.forms.events.filter.response
});

export default connect(mapStateToProps, {setFilter})(Dates);

