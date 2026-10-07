import http from "k6/http";
import { check } from "k6";

export const options = {
    stages: [
        { duration: "20s", target: 10 },
        { duration: "20s", target: 10 },
        { duration: "20s", target: 0 }, 
    ],
};

export default function () {
    const response = http.get("http://localhost:8080/users");

    check(response, {
        "Status is 200": (r) => r.status === 200,
    });
}