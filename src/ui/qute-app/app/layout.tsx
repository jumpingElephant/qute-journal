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
        <main>
            {children}
        </main>
        <footer className="page-footer">
            <div className="container">
                <div className="row">
                    <div className="col l6 s12">
                        <h5 className="white-text">Fußzeile</h5>
                        <p className="grey-text text-lighten-4">Der Inhalt der Fußzeile kann mit Zeilen und Spalten
                            gestaltet werden.</p>
                    </div>
                    <div className="col l4 offset-l2 s12">
                        <h5 className="white-text">Links</h5>
                        <ul>
                            <li><a className="grey-text text-lighten-3" href="https://quarkus.io/"
                                   target="_blank">Quarkus</a></li>
                            <li><a className="grey-text text-lighten-3" href="https://materializecss.com/"
                                   target="_blank">Materialize</a>
                            </li>
                            <li><a className="grey-text text-lighten-3" href="https://quarkus.io/blog/qute/"
                                   target="_blank">Quarkus
                                Qute</a></li>
                            <li><a className="grey-text text-lighten-3"
                                   href="https://www.mongodb.com/docs/manual/introduction/"
                                   target="_blank">MongoDB</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div className="footer-copyright">
                <div className="container">
                    © 2022 Copyright Text
                    <a className="grey-text text-lighten-4 right" href="https://github.com/jumpingElephant/qute-journal"
                       target="_blank">Code on
                        GitHub</a>
                </div>
            </div>
        </footer>
        </body>
        </html>
    );
}
