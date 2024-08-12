import {NextPage} from "next";
import Navbar from "@/app/components/navbar/navbar";
import Footer from "@/app/components/footer/footer";

const Layout: NextPage = () => {
    return (
        <main className="bg-darktwo h-screen">
            <Navbar />
            <Footer />
        </main>
    )
}

export default Layout;