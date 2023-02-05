import './css/materialize.css';
import './css/app.css';
import React from "react";

export default function RootLayout({children}: {
    children: React.ReactNode
}) {

    return (
        <html lang="de-de">
        <body className={"keyboard-focused"}>
        <header>
            <div className="navbar-fixed">
                <nav>
                    <div className="nav-wrapper">
                        <a href="{path:root}/{path:queryParams}" className="brand-logo center">TODO-App</a>
                        <ul id="nav-mobile" className="left hide-on-med-and-down">
                            <li className="active"><a href="{path:root}/{path:queryParams}"><i
                                className="material-icons left">event_note</i>Aufgaben</a></li>
                            <li><a href="{path:root}/dev/reset{path:queryParams}"><i
                                className="material-icons left">autorenew</i>Zurücksetzen</a></li>
                            <li><a href="http://localhost:8080/q/dev/" target="_blank"><i
                                className="material-icons left">developer_board</i>Quarkus
                                Dev UI</a></li>
                            <li><a href="http://localhost:8081/db/todoapp/" target="_blank"><i
                                className="material-icons left">explicit</i>Mongo Express</a></li>
                        </ul>
                    </div>
                </nav>
            </div>
        </header>
        {children}
        </body>
        </html>
    );
}
