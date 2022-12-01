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
        });
    }

    render() {
        const Button = Styled.button`
        background-color: #4CAF50; /* Green */
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
                        <Button
                            onClick={() => this.setState({ start: true })}
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
                    <Button
                        manualResult="same"
                        onClick={()=>this.clickHandler("same")}>
                        Same
                    </Button>
                    <Button
                        manualResult="different"
                        onClick={()=>this.clickHandler("different")}>
                        Different
                    </Button>
                    <Button
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
            </div>
        );
    }
}