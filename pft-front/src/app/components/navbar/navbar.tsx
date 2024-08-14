import React from "react";
import Link from "next/link";

const Navbar: React.FC = () => {
    return <div className="navbar bg-base-100">
        <div className="flex-1">
            <a className="btn btn-ghost text-xl">PFT</a>
        </div>
        <div className="flex justify-center flex-1">
            <ul className="menu menu-horizontal px-1">
                <li><a>Portfolio</a></li>
                <li><Link href="/pages/currencies">Tokens Listing</Link></li>
                <li><a>Assessments</a></li>
            </ul>
        </div>
        <div className="flex justify-end flex-1 gap-2">
            <div className="dropdown dropdown-end">
                <div tabIndex={0} role="button" className="btn btn-ghost btn-circle avatar">
                    <div className="w-10 rounded-full">
                        <img
                            alt="Tailwind CSS Navbar component"
                            src="https://img.daisyui.com/images/stock/photo-1534528741775-53994a69daeb.webp"/>
                    </div>
                </div>
                <ul
                    tabIndex={0}
                    className="menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow">
                    <li>
                        <a className="justify-between">
                            Profile
                            <span className="badge">New</span>
                        </a>
                    </li>
                    <li><a>Settings</a></li>
                    <li><Link href="/pages/signup">Login</Link></li>
                </ul>
            </div>
        </div>
    </div>
}

export default Navbar;