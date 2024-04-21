import clsx from "clsx";

interface ButtonProps extends React.ComponentProps<"button"> {
  buttonType: "primary" | "secondary";
  text: string;
}

export default function Button({
  buttonType,
  text,
  ...buttonProps
}: ButtonProps) {
  return (
    <button
      {...buttonProps}
      className={clsx(
        buttonType === "primary"
          ? "bg-blue-custom-900 px-[30px] py-3 rounded-[200px] text-white hover:bg-blue-custom-700 active:bg-blue-custom-900 disabled:opacity-20 focus:outline-[7px] focus:outline focus:outline-grey-focus visited:bg-blue-custom-500 transition-all ease-in-out"
          : "bg-transparent rounded-[200px] border-[1px] border-solid border-blue-custom-1100 px-[30px] py-3 text-blue-custom-1100 focus:bg-[#E0E5FB] disabled:opacity-20 transition-all ease-in-out"
      )}
    >
      <span className="font-medium">{text}</span>
    </button>
  );
}
