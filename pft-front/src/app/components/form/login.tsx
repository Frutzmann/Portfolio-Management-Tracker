const Login: React.FC = () => {
    return (
        <div style={{marginTop: "10px"}}>
            <div className="mt-5">
                <div className="mt-3 mb-2" align="left">
                    Email Address:
                </div>
                <input type="email" placeholder={"Email Address"} className={"input input-bordered input-accent text-darkzero w-full"}/>
            </div>
            <div className={"mt-5"}>
                <div className="mt-3 mb-2" align="left">
                    Password:
                </div>
                <input type="password" placeholder={"Password"} className={"input input-bordered input-accent text-darkzero w-full"} />
            </div>
            <div>
                <button className={"btn btn-wide btn-info mt-3"} align={"center"}>
                    Login
                </button>
            </div>
        </div>)
}

export default Login;