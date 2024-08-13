import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import NavBar from "@/app/components/navbar/navbar";
import Footer from "@/app/components/footer/footer";
import Layout from "@/app/components/layout/layout";


const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Portfolio Management Tracker",
  description: "Generated by create next app",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="fr">
      <body className={inter.className}>
        <NavBar />
        <main className={"bg-darktwo h-screen"}>
          {children}
        </main>
        <Footer />
      </body>
    </html>
  );
}
