import React from 'react';

import AutocompleteLocation from "forms/utils/components/AutocompleteLocation";
import ItemContainer from "../../Container/ItemContainer";

const AutocompleteLocationField = ({name, placeholder, setValue, error, value}) => {
    return <ItemContainer style={{height: 70}}>
        <AutocompleteLocation onChangeLocation={(location) => setValue(name, location)}
                              placeholder={placeholder}
                              value={value}
                              name='location'
                              error={error}
        /></ItemContainer>
};

export default AutocompleteLocationField;