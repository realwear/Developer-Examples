///////////////////////////////////////////////////////////////////////////////
//
// RealWear Development Software, Source Code and Object Code
// (c) RealWear, Inc. All rights reserved.
//
// Contact info@realwear.com for further information about the use of this
// code.
//
///////////////////////////////////////////////////////////////////////////////

using UnityEngine;
using UnityEngine.UI;
using WearHFPlugin;

/// <summary>
/// Class for manang the pause and resume camera buttons
/// </summary>
public class ButtonsController : MonoBehaviour
{
    /// <summary>
    /// The on-scren pause button
    /// </summary>
    public Button PauseButton;

    /// <summary>
    /// The on-screen resume button
    /// </summary>
    public Button ResumeButton;

    private WebCamTexture m_webcam = null;
    private WearHF m_wearHf;

    private Color m_colorEnabled = Color.white;
    private Color m_colorDisabled = Color.grey;

    /// <summary>
    /// Possible states for a button
    /// </summary>
    private enum ButtonState
    {
        Enabled,
        Disabled
    }

    /// <summary>
    /// See Unity docs
    /// </summary>
    private void Start()
    {
        m_webcam =
            GameObject.Find("RawImage").
            GetComponent<CameraController>().
            Webcam;

        m_wearHf = GameObject.Find("WearHF Manager").GetComponent<WearHF>();

        ToggleButtonState(
            PauseButton,
            PauseButton.interactable ?
                ButtonState.Enabled : ButtonState.Disabled);

        ToggleButtonState(
            ResumeButton,
            ResumeButton.interactable ?
                ButtonState.Enabled : ButtonState.Disabled);
    }

    /// <summary>
    /// Pause the camera
    /// </summary>
    public void PauseCamera()
    {
        m_webcam.Stop();

        ToggleButtonState(PauseButton, ButtonState.Disabled);
        ToggleButtonState(ResumeButton, ButtonState.Enabled);
    }

    /// <summary>
    /// Resume the camera
    /// </summary>
    public void ResumeCamera()
    {
        m_webcam.Play();

        ToggleButtonState(PauseButton, ButtonState.Enabled);
        ToggleButtonState(ResumeButton, ButtonState.Disabled);
    }

    /// <summary>
    /// Toggle the state of a button
    /// </summary>
    /// <param name="button">The button to toggle</param>
    /// <param name="state">The new state for the button</param>
    private void ToggleButtonState(Button button, ButtonState state)
    {
        Text buttonText = button.GetComponentInChildren<Text>();

        switch (state)
        {
            case ButtonState.Enabled:
                buttonText.color = m_colorEnabled;
                button.interactable = true;

                m_wearHf.AddVoiceCommand(
                    buttonText.text, VoiceCommandCallback);
                break;
            case ButtonState.Disabled:
                buttonText.color = m_colorDisabled;
                button.interactable = false;

                m_wearHf.RemoveVoiceCommand(buttonText.text);
                break;
        }
    }

    /// <summary>
    /// Called when a voice command is triggered by the user
    /// </summary>
    /// <param name="voiceCommand">The voice command that was triggered</param>
    private void VoiceCommandCallback(string voiceCommand)
    {
        if (PauseButton.GetComponentInChildren<Text>().text == voiceCommand)
        {
            PauseCamera();
        }
        else if (
            ResumeButton.GetComponentInChildren<Text>().text == voiceCommand)
        {
            ResumeCamera();
        }
    }
}
