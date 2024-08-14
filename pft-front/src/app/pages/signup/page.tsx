"use client";
import {NextPage} from "next";
import Form from "@/app/components/form";
import {useState} from "react";
import {Skeleton} from "@mui/material";

const SignUp: NextPage = () => {
    const [isLoading, setIsLoading] = useState(false);
/*
    if(!isLoading) {
        return(
            <div className={"middle"}>
                <div className="rounded-lg mt-3 text-white bg-indigo-500 p-5 w-[90%] m-auto lg:w-[800px] md:w-[800px]"
                     align="center">
                    <Skeleton variant={"rectangular"} width={"90%"} height={"300"} />
                </div>
            </div>
        )
    }
*/
    return (
        <div className={"middle"}>
            <Form />
        </div>
    )

}

export default SignUp;