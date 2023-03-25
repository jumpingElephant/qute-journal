'use client';
import React, { useState } from "react";
import { createTask, deleteTask, getAllTasks, updateTask } from "../TaskService";
import { Task } from "../Task";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { HREF_TASKS } from "../../Hrefs";

export type TaskPageProps = {
  task: Task,
  isNewTask: boolean
}

export default function TaskPage(props: TaskPageProps) {
  const [task, setTask] = useState<Task>(props.task);
  const [isLoading, setLoading] = useState(false);
  const router = useRouter();

  const onSubmitTask = () =>
      (props.isNewTask ? createTask(task) : updateTask(task))
          .finally(() => {
            router.push(HREF_TASKS);
            router.refresh();
          });
  const onDeleteTask = () => deleteTask(task.key)
      .then(() => getAllTasks())
      .finally(() => {
        router.push(HREF_TASKS)
        router.refresh();
      })
  const onKeyDown = (e: any) => {
    if (e.keyCode === 13) {
      onSubmitTask();
    }
  }

  const onTitleChanged = (e: any) => setTask({...task, title: e.target.value})
  const onDuedateChanged = (e: any) => setTask({...task, dueDate: e.target.value})

  if (isLoading) return <p>Loading...</p>

  if (!task) return <p>No tasks</p>

  return (
      <div className="container">
        <div className="row">
          <div className="col s12 xl8 offset-m1">
            <div className="section">
              <div className="row">
                <div className="col s12">
                  <h3 className="header">Edit Task</h3>

                  <article>
                    <div className="col s12">
                      <div className="row">
                        <div className="col s6">
                          <p className="caption">Edit your task using Quarkus,
                            RESTEasy & <a href={'https://nextjs.org'}
                                          target={'_blank'}>next.js</a>
                          </p>
                        </div>
                      </div>
                      <section>
                        <div className="row" aria-label="list of tasks">
                          <div className="col s12">
                            <div className="card">
                              <div className="card-content">
                                <div className="row">
                                  <div className="col s6">
                                    <label htmlFor="task_title">Title</label>
                                    <input placeholder="Title is required"
                                           id="task_title" type="text"
                                           autoFocus={true}
                                           value={task.title}
                                           onChange={onTitleChanged}
                                           onKeyDown={onKeyDown}>
                                    </input>
                                  </div>
                                  <div className="col s6">
                                    <label htmlFor="task_due_date">Due Date</label>
                                    <input placeholder="Due Date" id="task_due_date"
                                           type="date"
                                           value={task.dueDate ? task.dueDate : undefined}
                                           onChange={onDuedateChanged}
                                           onKeyDown={onKeyDown}>
                                    </input>
                                  </div>
                                </div>
                                <div className="card-action">
                                  <div className="row s12">
                                    <div className="col s8">
                                      <a className="waves-effect waves-light btn"
                                         onClick={onSubmitTask}><i
                                          className="material-icons left">save</i>Submit</a>
                                      {!props.isNewTask &&
                                          <a className="waves-effect waves-light btn"
                                             onClick={onDeleteTask}><i
                                              className="material-icons left">delete</i>Delete</a>
                                      }
                                    </div>
                                    <div className="col s4 right-align">
                                      <Link className="waves-effect waves-light btn"
                                            href={HREF_TASKS}><i
                                          className="material-icons left">cancel</i>Cancel</Link>
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </section>
                    </div>
                  </article>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
}
