import React from "react";
import TextField from "@material-ui/core/TextField";
import PropTypes from 'prop-types';

const LabeledInput = ({
                          value, onChange, isError, errorText, id, label,
                          defaultValue, multiline, rowsMax, type, inputRef
                      }) => {
    const onChangeValue = (evt) => {
        onChange(evt.target.value)
    };

    let textFieldProps;

    if (inputRef) {
        textFieldProps = {
            inputRef: inputRef
        }
    } else {
        textFieldProps = {
            type,
            multiline,
            error: isError,
            rowsMax,
            helperText: errorText,
            value,
            defaultValue,
            onChange: onChangeValue,
        }
    }

    return <TextField style={{background: 'white'}}
                      label={label}
                      variant="outlined"
                      id={id}
                      {...textFieldProps}/>
};

LabeledInput.propTypes = {
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
    onChange: PropTypes.func,
    isError: PropTypes.bool,
    errorText: PropTypes.string,
    id: PropTypes.string.isRequired,
    label: PropTypes.string,
    defaultValue: PropTypes.string,
    multiline: PropTypes.bool,
    rowsMax: PropTypes.number,
    type: PropTypes.string
};

LabeledInput.defaultProps = {
    type: 'text',
    onChange: () => null
};

export default LabeledInput;