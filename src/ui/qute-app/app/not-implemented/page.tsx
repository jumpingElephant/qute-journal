import Link from "next/link";
import { HREF_TASKS } from "../Hrefs";

export type NotImplementedArguments = {
  params: {
    taskid: string,
    searchParams: any
  }
}

export default async function Page(args: NotImplementedArguments) {

  return (
      <div className="container">
        <div className="row">
          <div className="col s12 xl8 offset-m1">
            <div className="section">
              <div className="row">
                <div className="col s12">
                  <h3 className="header">This feature is not yet implemented!</h3>
                  <Link href={HREF_TASKS}>Go back</Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>);
};
