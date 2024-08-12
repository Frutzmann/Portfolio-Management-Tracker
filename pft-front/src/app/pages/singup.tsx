import {NextPage} from "next";
import {useRouter} from "next/router";
import Form from "@/app/components/form";

const Signup : NextPage = () => {
    const router = useRouter();

    return (
        <div>
            <Form />
        </div>
    )

}

export default Signup;