import {NextPage} from "next";
import {ChangeEvent, useState} from "react";
import Switch from "@mui/material/Switch";
import SignUp from "@/app/components/form/signup";
import Login from "@/app/components/form/login";

const Form: React.FC = () => {
    const [checked, setChecked] = useState(false);

    const handleChange = (event: ChangeEvent<HTMLInputElement>): void => {
        setChecked(event.target.checked)
    }

    return <div style={{border: "1px solid green", boxShadow: "0px 0px 50 px rgb(26 173 46 / 15%"}}
                className="rounded-lg mt-3 text-white bg-indigo-500 p-5 w-[90%] m-auto lg:w-[800px] md:w-[800px]">
        <div className="p-5 w-full">
            <Switch
                checked={checked}
                onChange={handleChange}
                input-props={{"aria-label": "controlled"}}
            />
            {checked ? (
                <SignUp />
            ) : (
                <Login />
            )}
        </div>

    </div>
}

export default Form;