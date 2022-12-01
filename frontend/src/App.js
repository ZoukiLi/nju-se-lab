import React from "react";
import ReactDiffViewer, { DiffMethod } from "react-diff-viewer";
import Styled from "styled-components";

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            end: false,
            start: false,
            oldString: "",
            newString: "",
            leftCaption: "",
            rightCaption: "",
            testCases: [],
            leftResults: [],
            rightResults: [],
        };
    }

    clickHandler = async (sender) => {
        const manualResult = sender;
        console.log("manualResult", manualResult);
        const results = await fetch("/api/nextComparison", {
            method: "Post",
            headers: {
                "Content-Type": "application/json",
            },
            body: `{"manualResult":"${manualResult}"}`,
        });
        const json = await results.json();
        this.setState({
            end: json.end,
            oldString: json.leftContent,
            newString: json.rightContent,
            leftCaption: json.leftCaption,
            rightCaption: json.rightCaption,
            testCases: json.testCases,
            leftResults: json.leftResults,
            rightResults: json.rightResults
        });
    }

    render() {
        const Button = Styled.button`
        background-color: ${props => props.color};
        border: none;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        `;
        if (this.state.end) {
            return (
                <div className="App">
                    <div>
                        <h1>
                            All files have been compared.
                        </h1>
                        <p>
                            Comparison results are saved to output directory.
                        </p>
                    </div>
                </div>
            );
        }
        if (!this.state.start) {
            return (
                <div className="App">
                    <div>
                        <h1>
                            Welcome to the comparison tool.
                        </h1>
                        <p>
                            Click the button below to start the comparison.
                        </p>
                        <Button color="green"
                            onClick={() => this.clickHandler("start").then(
                                () => this.setState({ start: true })
                            )}
                        >
                            Start
                        </Button>
                    </div>
                </div>
            );
        }
        return (
            <div className="App">
                <div>
                    <Button color="green"
                        manualResult="same"
                        onClick={()=>this.clickHandler("same")}>
                        Same
                    </Button>
                    <Button color="red"
                        manualResult="different"
                        onClick={()=>this.clickHandler("different")}>
                        Different
                    </Button>
                    <Button color="brown"
                        manualResult="notSure"
                        onClick={()=>this.clickHandler("notSure")}>
                        Not Sure
                    </Button>
                </div>
                <ReactDiffViewer
                    oldValue={this.state.oldString}
                    newValue={this.state.newString}
                    splitView={true}
                    compareMethod={DiffMethod.WORDS}
                    leftTitle={this.state.leftCaption}
                    rightTitle={this.state.rightCaption}
                />

                <p>
                    <b>Total {this.state.testCases.length} Test Cases:</b>
                </p>

                <div>
                    {this.state.testCases.map((testCase, index) => (
                        <ReactDiffViewer
                            oldValue={
                            `
stdout: ${this.state.leftResults[index].stdout}\n
stderr: ${this.state.leftResults[index].stderr}\n
${this.state.leftResults[index].timeout ? "timeout" : ""}
                            `
                            }
                            newValue={
                            `
stdout: ${this.state.rightResults[index].stdout}\n
stderr: ${this.state.rightResults[index].stderr}\n
${this.state.rightResults[index].timeout ? "timeout" : ""}
                            `
                            }
                            splitView={true}
                            compareMethod={DiffMethod.WORDS}
                            leftTitle={`test case${index}: ${testCase}`}
                            rightTitle={""}
                        />
                    ))}
                </div>
            </div>
        );
    }
}