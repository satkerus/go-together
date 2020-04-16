import React, {useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
import CircularProgress from '@material-ui/core/CircularProgress';
import {connect} from "../../../Context";
import {withStyles} from "@material-ui/core";
import {FORM_ID} from "./constants";
import {getOptions} from "./actions";

const StyledAutocomplete = withStyles({
    inputRoot: {
        flexWrap: 'inherit',
        padding: '14px 6px'
    },
    input: {
        height: '28px'
    }
})(Autocomplete);

const IntegrationAutosuggest = ({formId, setResult, placeholder, url, urlParam, getOptions, options}) => {
    const [open, setOpen] = useState(false);
    const [value, setValue] = useState('');
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const chooseItem = options.filter(option => option.name === value)[0];
        if (chooseItem) {
            setResult(chooseItem);
        }
    }, [options, value, setResult]);

    useEffect(() => {
        const chooseItem = options.filter(option => option.name === value)[0];
        if (!chooseItem) {
            setLoading(true);
            getOptions(url, urlParam, value, setLoading);
        }
    }, [getOptions, value, url, urlParam]);

    return (
        <StyledAutocomplete
            id={formId + "autocomplete"}
            style={{width: 400}}
            open={open}
            onOpen={() => {
                setOpen(true);
            }}
            onClose={() => {
                setOpen(false);
            }}
            getOptionLabel={option => option.name}
            options={options}
            loading={loading}
            onInputChange={(evt, value) => setValue(value)}
            renderInput={params => <TextField
                {...params}
                label={placeholder}
                fullWidth
                variant="outlined"
                InputProps={{
                    ...params.InputProps,
                    endAdornment: (
                        <>
                            {loading ? <CircularProgress
                                color="inherit"
                                size={20}/> : null}
                            {params.InputProps.endAdornment}
                        </>
                    ),
                }}
            />
            }
        />
    );
};


IntegrationAutosuggest.propTypes = {
    setResult: PropTypes.func.isRequired,
    placeholder: PropTypes.string.isRequired,
    url: PropTypes.string.isRequired,
    urlParam: PropTypes.string.isRequired,
    formId: PropTypes.string.isRequired,
    getOptions: PropTypes.func.isRequired,
    options: PropTypes.array.isRequired
};

const mapStateToProps = state => ({
    options: state[FORM_ID]?.options || []
});


export default connect(mapStateToProps, {getOptions}, FORM_ID)(IntegrationAutosuggest);